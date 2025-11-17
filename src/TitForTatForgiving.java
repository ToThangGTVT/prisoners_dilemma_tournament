import java.util.concurrent.ThreadLocalRandom; // Đảm bảo bạn đã import cái này

/**
 * Chiến lược: Tit for Tat Vị tha (Forgiving Tit for Tat).
 * Giống như Tit for Tat, nhưng có một cơ hội nhỏ (ví dụ: 10%)
 * để "tha thứ" (Hợp tác) ngay cả khi đối thủ vừa Phản bội,
 * nhằm phá vỡ vòng xoáy trả đũa.
 */
class TitForTatForgiving implements Strategy {

    // Đặt xác suất tha thứ (ví dụ: 10%)
    private static final double FORGIVENESS_CHANCE = 0.1;

    @Override
    public Move getNextMove(Move lastOpponentMove) {
        if (lastOpponentMove == null) {
            // Vòng đầu tiên: Luôn hợp tác
            return Move.COOPERATE;
        }

        if (lastOpponentMove == Move.COOPERATE) {
            // Đối thủ hợp tác: Ta hợp tác
            return Move.COOPERATE;
        }

        // Nếu đối thủ vừa phản bội (DEFECT)
        // Ta sẽ xem xét việc "tha thứ"
        if (ThreadLocalRandom.current().nextDouble() < FORGIVENESS_CHANCE) {
            // Tha thứ! Cố gắng hợp tác để phá vỡ chu kỳ
            return Move.COOPERATE;
        } else {
            // Không tha thứ, trả đũa như Tit for Tat bình thường
            return Move.DEFECT;
        }
    }

    @Override
    public void reset() {
        // Không cần trạng thái
    }

    @Override
    public String getName() {
        return "Tit for Tat Forgiving (TFT Vị tha)";
    }
}