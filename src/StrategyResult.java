/**
 * Lớp chính điều khiển giải đấu.
 */

class StrategyResult {
    String name;
    int count = 0;
    int totalScore = 0;
    double averageScore = 0.0;

    StrategyResult(String name) {
        this.name = name;
    }
}
