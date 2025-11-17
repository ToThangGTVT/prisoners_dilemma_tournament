/**
 * Chiến lược: Tit for Tat (Ăn miếng trả miếng).
 * - Vòng đầu tiên: Hợp tác.
 * - Các vòng sau: Sao chép nước đi cuối cùng của đối thủ.
 */
class TitForTat implements Strategy {
    @Override
    public Move getNextMove(Move lastOpponentMove) {
        if (lastOpponentMove == null) {
            // Đây là vòng đầu tiên, hãy hợp tác
            return Move.COOPERATE;
        }
        // Sao chép nước đi cuối cùng của đối thủ
        return lastOpponentMove;
    }

    @Override
    public void reset() {
        // Không cần trạng thái, vì quyết định chỉ dựa vào nước đi trước đó
    }

    @Override
    public String getName() {
        return "Tit for Tat (Ăn miếng trả miếng)";
    }
}
