package bridge.domain;

import java.util.List;
import java.util.stream.Collectors;

public class Bridge {
    private final List<UpDownFlag> bridge;

    private Bridge(List<UpDownFlag> bridge) {
        this.bridge = bridge;
    }

    public static Bridge of(List<String> bridge) {
        List<UpDownFlag> collect = bridge.stream()
                .map(UpDownFlag::ofString)
                .collect(Collectors.toList());
        return new Bridge(collect);
    }

    public List<UpDownFlag> getBridge() {
        return List.copyOf(bridge);
    }

    public int getLength() {
        return bridge.size();
    }

    public UpDownFlag getIndexOf(int index) {
        return bridge.get(index);
    }
}
