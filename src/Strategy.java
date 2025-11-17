/**
 * Interface cho một chiến lược chơi.
 * Mỗi chiến lược phải quyết định nước đi tiếp theo
 * dựa trên nước đi cuối cùng của đối thủ.
 */
interface Strategy {
    /**
     * Quyết định nước đi tiếp theo.
     *
     * @param lastOpponentMove Nước đi cuối cùng của đối thủ (null nếu đây là vòng đầu tiên).
     * @return Nước đi của chiến lược này (COOPERATE hoặc DEFECT).
     */
    Move getNextMove(Move lastOpponentMove);

    /**
     * Reset lại trạng thái của chiến lược cho một trận đấu mới.
     */
    void reset();

    /**
     * @return Tên của chiến lược.
     */
    String getName();
}
