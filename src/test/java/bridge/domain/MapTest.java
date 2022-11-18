package bridge.domain;

import static org.assertj.core.api.Assertions.*;

import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MapTest {
    Map map;
    Bridge bridge;
    User user;

    @BeforeEach
    void setUp() {
        bridge = Bridge.of(List.of("U", "U", "U"));
        user = new User();
        map = new Map(bridge, user);
    }

    @Test
    @DisplayName("맵을 그릴 때 사용자의 길이가 다리의 길이보다 길면 예외가 발생한다.")
    void drawMapByUserLengthOverBridgeLength() {
        // given
        user.move(UpDownFlag.UP);
        user.move(UpDownFlag.UP);
        user.move(UpDownFlag.UP);
        user.move(UpDownFlag.UP);
        // expect
        assertThatThrownBy(() -> map.update())
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("맵을 그릴 때 사용자가 잘못된 다리를 밟으면 해당 위치에 X가 있어야한다.")
    void drawMapByUserWrongLocationShouldBeX() {
        // given
        user.move(UpDownFlag.UP);
        user.move(UpDownFlag.DOWN);
        user.move(UpDownFlag.UP);
        // when
        map.update();
        // then
        assertThat(map.getUpper())
                .isEqualTo(List.of(BridgeFlag.SUCCESS, BridgeFlag.NOTHING, BridgeFlag.SUCCESS));
        assertThat(map.getLower())
                .isEqualTo(List.of(BridgeFlag.NOTHING, BridgeFlag.FAIL, BridgeFlag.NOTHING));
    }

    @Test
    @DisplayName("맵을 그릴 때 사용자가 다리를 밟으면 해당 위치에 O가 있어야한다.")
    void drawMapByUserLocationShouldBeO() {
        // given
        user.move(UpDownFlag.UP);
        user.move(UpDownFlag.UP);
        user.move(UpDownFlag.UP);
        // when
        map.update();
        // then
        assertThat(map.getUpper())
                .isEqualTo(List.of(BridgeFlag.SUCCESS, BridgeFlag.SUCCESS, BridgeFlag.SUCCESS));
        assertThat(map.getLower())
                .isEqualTo(List.of(BridgeFlag.NOTHING, BridgeFlag.NOTHING, BridgeFlag.NOTHING));
    }

    @Test
    @DisplayName("맵을 그릴 때 사용자가 다리를 밟지 않으면 아무것도 그리지 않는다.")
    void drawMapByUserNoCrossing() {
        // given

        // when
        map.update();

        // then
        assertThat(map.getUpper())
                .isEqualTo(Collections.emptyList());
        assertThat(map.getLower())
                .isEqualTo(Collections.emptyList());
    }
}