/**
 * Chiến lược: Luôn luôn Phản bội.
 */
class AlwaysDefect implements Strategy {
    @Override
    public Move getNextMove(Move lastOpponentMove) {
        return Move.DEFECT;
    }

    @Override
    public void reset() {
        // Không cần trạng thái
    }

    @Override
    public String getName() {
        return "Always Defect (Luôn Phản bội)";
    }
}
