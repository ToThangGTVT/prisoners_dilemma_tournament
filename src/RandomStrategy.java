import java.util.concurrent.ThreadLocalRandom;
/**
 * Chiến lược: Chơi ngẫu nhiên (50% Hợp tác, 50% Phản bội).
 */
class RandomStrategy implements Strategy {

    @Override
    public Move getNextMove(Move lastOpponentMove) {
        if (ThreadLocalRandom.current().nextBoolean()) {
            return Move.COOPERATE;
        } else {
            return Move.DEFECT;
        }
    }

    @Override
    public void reset() {
        // Không cần trạng thái
    }

    @Override
    public String getName() {
        return "Random (Ngẫu nhiên)";
    }
}
