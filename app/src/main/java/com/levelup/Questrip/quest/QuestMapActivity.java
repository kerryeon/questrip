package com.levelup.Questrip.quest;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.levelup.Questrip.R;
import com.levelup.Questrip.about.AboutActivity;
import com.levelup.Questrip.common.CommonAlert;
import com.levelup.Questrip.common.QuestManager;
import com.levelup.Questrip.config.ConfigActivity;
import com.levelup.Questrip.data.Account;
import com.levelup.Questrip.data.Quest;
import com.levelup.Questrip.net.ClientRequestAsync;
import com.levelup.Questrip.view.ViewActivity;

/**
 * 지도 위에 퀘스트를 표시해주는 액티비티입니다.
 * 퀘스트를 터치하면 해당 퀘스트에 대한 간략한 정보를 보여주는 레이아웃이 아래서부터 올라옵니다.
 * 한편, 상단 좌측에 = 같은 모양의 버튼을 두어, 메뉴창을 열 수 있게 합니다.
 *
 * 담당자: 이동욱, 구본근
 *
 * 역할: 지도 위에 퀘스트를 표시합니다.
 * 퀘스트는 MarkerManager 를 통해 표시합니다.
 *
 * 퀘스트를 터치하면 레이아웃이 아래서부터 올라오는데, 해당 레이아웃에는 다음과 같은 정보가 있어야 합니다.
 * 퀘스트 제목
 * 퀘스트 설명
 * 퀘스트 위치 (지명)
 * 퀘스트 시작일 및 종료일
 * 리더보드 버튼 - 리더보드 보기 화면으로 이동합니다.
 * @see ViewActivity
 */
public final class QuestMapActivity extends FragmentActivity implements NavigationView.OnNavigationItemSelectedListener {

    private MapViewManager manager;

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private QuestAboutLayout questAboutLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest_map);
        init();
    }

    /**
     * 변수 및 필드를 초기화합니다.
     */
    private void init() {
        // SupportMapFragment 객체를 획득한 후, 지도가 사용 가능한 지 검사합니다.
        manager = new MapViewManager(this, this::onMapReady, this::onShowQuestInfo);

        // 필드
        drawerLayout = findViewById(R.id.quest_map);
        navigationView = drawerLayout.findViewById(R.id.quest_map_nav_menu);
        questAboutLayout = new QuestAboutLayout(this);

        // 닉네임, 집주소 추가
        View header = navigationView.getHeaderView(0);
        TextView fieldNickname = header.findViewById(R.id.quest_map_nickname);
        fieldNickname.setText(Account.getInstance().getNickname());
        TextView fieldAddress = header.findViewById(R.id.quest_map_address);
        fieldAddress.setText(Account.getInstance().getAddress());

        // 이벤트
        navigationView.setNavigationItemSelectedListener(this);
    }

    /**
     * 지도가 사용 가능할 때 발생하는 이벤트입니다.
     * 실질적으로 액티비티를 사용할 준비가 된 경우입니다.
     */
    public void onMapReady() {
        tryUpdateList();
    }

    /**
     * 서버로부터 퀘스트 목록을 가져옵니다.
     * 목록을 수신받지 못하면, 그 이유를 알린 후 앱을 종료합니다.
     */
    private void tryUpdateList() {
        QuestManager.updateList(this::onSuccessUpdateList, this::onFailureFatal);
    }

    /**
     * 서버로부터 퀘스트 목록을 성공적으로 불러온 경우입니다.
     * 마커를 갱신하고, 조금뒤 거리 순에서 보여줍니다.
     */
    private void onSuccessUpdateList() {
        manager.updateMarkers();
        // 잠시 후, 화면을 현재 위치가 중앙으로 보이게 하고, 거리 순에서 마커를 보여줍니다.
        Handler handler = new Handler();
        handler.postDelayed(manager::updateMarkersByDistance,
                getResources().getInteger(R.integer.CODE_ACTIVITY_QUEST_MAP_WAIT_FOCUS));
    }

    /**
     * 앱 구동에 필수적인 요청이 거절된 경우의 이벤트입니다.
     * @param onFailure 실패 이유
     */
    private void onFailureFatal(ClientRequestAsync.Failed onFailure) {
        CommonAlert.failed(this, onFailure, this::finishAndRemoveTask);
    }

    /**
     * 사용자 정보 보기 버튼을 눌렀을 경우의 이벤트입니다.
     * 사용자 정보 액티비티로 이동합니다.
     */
    private void showAbout() {
        Intent intent = new Intent(getApplicationContext(), AboutActivity.class);
        startActivity(intent);
    }

    /**
     * 환경설정 버튼을 눌렀을 경우의 이벤트입니다.
     * 환경설정 액티비티로 이동합니다.
     */
    private void showConfig() {
        Intent intent = new Intent(getApplicationContext(), ConfigActivity.class);
        startActivityForResult(intent, getResources().getInteger(R.integer.CODE_INTENT_CONFIG));
    }

    /**
     * 메뉴창이 열려있는 지 검사합니다.
     * @return 메뉴창이 열려있다면 true 를 반환합니다.
     */
    private boolean isMenuOpen() {
        return drawerLayout.isDrawerOpen(navigationView);
    }

    /**
     * 메뉴창을 엽니다.
     */
    private void openMenu() {
        // 퀘스트 설명창은 닫습니다.
        questAboutLayout.hide();
        drawerLayout.openDrawer(navigationView);
    }

    /**
     * 메뉴창을 닫습니다.
     */
    private void closeMenu() {
        if (isMenuOpen())
            drawerLayout.closeDrawer(navigationView);
    }

    /**
     * 마커를 눌러 해당 퀘스트의 정보를 보려는 경우의 이벤트입니다.
     * 마커가 아닌 맵을 터치한 경우, 퀘스트 정보창을 내립니다.
     * @param quest 퀘스트. null 인 경우, 맵을 터치한 경우.
     */
    private void onShowQuestInfo(final Quest quest) {
        // 맵을 터치한 경우, 퀘스트 정보창을 내립니다.
        if (quest == null) questAboutLayout.hide();
        // 마커를 터치한 경우, 퀘스트 정보창을 띄웁니다.
        else questAboutLayout.show(quest);
    }

    /**
     * 메뉴 버튼을 눌렀을 경우의 이벤트입니다.
     * 메뉴를 보여줍니다.
     * @param view 메뉴 버튼
     */
    public void onToggleMenu(View view) {
        openMenu();
    }

    /**
     * "리더보드" 버튼을 통해 퀘스트의 리더보드를 조회하려는 경우의 이벤트입니다.
     * 리더보드 화면으로 이동합니다.
     * @param view 메뉴 버튼
     */
    public void onShowLeaderBoard(View view) {
        final Quest quest = questAboutLayout.getCurrentQuest();
        // 리더보드 화면으로 이동합니다.
        Intent intent = new Intent(getApplicationContext(), ViewActivity.class);
        // 퀘스트 정보를 전달합니다.
        intent.putExtra("quest", quest);
        startActivity(intent);
    }

    /**
     * 메뉴의 어떤 항목을 선택한 경우의 이벤트입니다.
     * 메뉴창을 닫고, 각 항목의 역할을 수행합니다.
     * @param item 항목
     * @return 항목이 선택된 경우의 이펙트를 적용하려면 true 를 반환합니다.
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // 일단 메뉴는 닫습니다.
        closeMenu();
        // 각 항목의 역할을 수행합니다.
        switch (item.getItemId()) {
            case R.id.quest_map_item_about:
                showAbout();
                break;
            case R.id.quest_map_item_config:
                showConfig();
                break;
        }
        return false;
    }

    /**
     * 하위 액티비티로부터 결과를 수신받습니다.
     * @param requestCode: 요청 코드
     * @param resultCode: 응답 코드
     * @param data: 결과값을 포함한 객체
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 유효한 응답에만 반응합니다.
        if (resultCode != RESULT_OK) return;
        // 환경설정으로부터 앱 종료를 입력받았을 경우의 이벤트입니다.
        if (requestCode == getResources().getInteger(R.integer.CODE_INTENT_CONFIG)) {
            final boolean exit = data.getBooleanExtra("exit", false);
            if (exit) finishAndRemoveTask();
        }
    }

    /**
     * 뒤로가기 버튼을 누른 경우, 메뉴창을 닫거나 앱을 종료할 것인지 물어봅니다.
     */
    @Override
    public void onBackPressed() {
        // 메뉴창이 열려있는 경우, 메뉴창을 닫습니다.
        if (isMenuOpen()) closeMenu();
        // 퀘스트 설명창이 열러있는 경우, 창을 닫습니다.
        else if (questAboutLayout.isOpen()) questAboutLayout.hide();
        // 아무것도 열러있지 않은 경우, 앱을 종료할 것인지 물어봅니다.
        else CommonAlert.closeApp(this);
    }

}
