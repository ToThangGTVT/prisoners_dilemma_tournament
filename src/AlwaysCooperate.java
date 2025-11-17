/**
 * Chiến lược: Luôn luôn Hợp tác.
 */
class AlwaysCooperate implements Strategy {
    @Override
    public Move getNextMove(Move lastOpponentMove) {
        return Move.COOPERATE;
    }

    @Override
    public void reset() {
        // Không cần trạng thái
    }

    @Override
    public String getName() {
        return "Always Cooperate (Dĩ hòa vi quý)";
    }
}
