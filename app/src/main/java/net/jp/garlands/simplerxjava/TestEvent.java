package net.jp.garlands.simplerxjava;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TestEvent {
    public enum Result {
        SUCCESS,
        FAILTURE
    }

    String statusString;
    Result statusResult;

    public String getStatusString() { return this.statusString; }
    public Result getStatusResult() { return this.statusResult; }
}
