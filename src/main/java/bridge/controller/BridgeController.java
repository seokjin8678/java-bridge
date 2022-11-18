package bridge.controller;

import bridge.domain.Bridge;
import bridge.domain.BridgeFlag;
import bridge.domain.User;
import bridge.model.BridgeGame;
import bridge.service.BridgeMaker;
import bridge.view.BridgeView;
import java.util.List;

public class BridgeController {
    private final BridgeView bridgeView;
    private final BridgeMaker bridgeMaker;
    private BridgeGame bridgeGame;

    public BridgeController(BridgeView bridgeView, BridgeMaker bridgeMaker) {
        this.bridgeView = bridgeView;
        this.bridgeMaker = bridgeMaker;
    }

    public void gameStart() {
        init();
        while (loop() != GameFlag.QUIT) {
            bridgeGame.retry();
        }
        bridgeView.printResult(bridgeGame.getMap(), bridgeGame.isCrossOver(), bridgeGame.getTryCount());
    }

    private GameFlag loop() {
        bridgeGame.move(bridgeView.receiveMoving());
        bridgeView.printMap(bridgeGame.getMap());
        BridgeFlag flag = bridgeGame.isCrossOver();
        if (flag == BridgeFlag.SUCCESS) {
            return GameFlag.QUIT;
        }
        if (flag == BridgeFlag.FAIL) {
            return bridgeView.receiveRestart();
        }
        return loop();
    }

    private void init() {
        bridgeView.printStartMessage();
        List<String> strings = bridgeMaker.makeBridge(bridgeView.receiveBridgeSize());
        bridgeGame = new BridgeGame(new User(), Bridge.of(strings));
    }
}
