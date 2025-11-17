import java.util.*;
import java.util.stream.Collectors;

public class PrisonersDilemmaTournament {

    // Định nghĩa điểm số dựa trên video
    private static final int BOTH_COOPERATE_SCORE = 3;
    private static final int DEFECTOR_SCORE = 5;       // Điểm cho người phản bội khi người kia hợp tác
    private static final int COOPERATOR_SCORE = 0;     // Điểm cho người hợp tác khi người kia phản bội
    private static final int BOTH_DEFECT_SCORE = 1;

    private static final int NUM_ROUNDS_PER_MATCH = 200; // Số vòng mỗi trận (như trong video)

    private List<Strategy> strategies;
    private Map<String, Integer> totalScores;

    public PrisonersDilemmaTournament() {
        strategies = new ArrayList<>();
        totalScores = new HashMap<>();
    }

    /**
     * Thêm các chiến lược vào giải đấu.
     */
    public void addStrategies() {
        strategies.add(new AlwaysCooperate());
        strategies.add(new AlwaysCooperate());
        strategies.add(new AlwaysDefect());
        strategies.add(new AlwaysDefect());
        strategies.add(new TitForTat());
        strategies.add(new TitForTat());
        strategies.add(new TitForTat());
        strategies.add(new TitForTatForgiving());
        strategies.add(new TitForTatForgiving());
        strategies.add(new RandomStrategy());
        strategies.add(new RandomStrategy());
        strategies.add(new RandomStrategy());
        strategies.add(new RandomStrategy());
        strategies.add(new RandomStrategy());
        strategies.add(new RandomStrategy());
        strategies.add(new RandomStrategy());
        strategies.add(new RandomStrategy());
        strategies.add(new RandomStrategy());
        strategies.add(new RandomStrategy());
        strategies.add(new RandomStrategy());
        strategies.add(new RandomStrategy());
        strategies.add(new RandomStrategy());
        strategies.add(new RandomStrategy());
        strategies.add(new RandomStrategy());
        strategies.add(new RandomStrategy());
        strategies.add(new RandomStrategy());
        strategies.add(new RandomStrategy());
        strategies.add(new RandomStrategy());
        strategies.add(new RandomStrategy());
        strategies.add(new RandomStrategy());
        strategies.add(new RandomStrategy());
        strategies.add(new RandomStrategy());
        // Bạn có thể thêm các chiến lược phức tạp khác ở đây (ví dụ: Tit for Two Tats)

        // Khởi tạo điểm số
        for (Strategy s : strategies) {
            String name = s.getName();
            int count = 1;
            while (totalScores.containsKey(name)) {
                name = s.getName() + " #" + (++count);
            }
            totalScores.put(name, 0);
        }
    }

    /**
     * Chạy toàn bộ giải đấu, cho mọi chiến lược đấu với mọi chiến lược khác.
     */
    public void runTournament() {
        System.out.println("Bắt đầu giải đấu với " + NUM_ROUNDS_PER_MATCH + " vòng mỗi trận...");
        System.out.println("--------------------------------------------------");

        for (int i = 0; i < strategies.size(); i++) {
            for (int j = i; j < strategies.size(); j++) {
                // Cho mỗi cặp chiến lược (kể cả đấu với chính mình)
                Strategy p1 = strategies.get(i);
                Strategy p2 = strategies.get(j);

                // Chạy một trận đấu
                playMatch(p1, p2);
            }
        }
    }

    /**
     * Mô phỏng một trận đấu giữa hai chiến lược.
     */
    private void playMatch(Strategy p1, Strategy p2) {
        // Reset trạng thái cho trận đấu mới
        p1.reset();
        p2.reset();

        Move p1LastMove = null;
        Move p2LastMove = null;

        int p1MatchScore = 0;
        int p2MatchScore = 0;

        for (int round = 0; round < NUM_ROUNDS_PER_MATCH; round++) {
            Move p1Move = p1.getNextMove(p2LastMove);
            Move p2Move = p2.getNextMove(p1LastMove);

            // Tính điểm cho vòng này
            if (p1Move == Move.COOPERATE && p2Move == Move.COOPERATE) {
                p1MatchScore += BOTH_COOPERATE_SCORE;
                p2MatchScore += BOTH_COOPERATE_SCORE;
            } else if (p1Move == Move.COOPERATE && p2Move == Move.DEFECT) {
                p1MatchScore += COOPERATOR_SCORE;
                p2MatchScore += DEFECTOR_SCORE;
            } else if (p1Move == Move.DEFECT && p2Move == Move.COOPERATE) {
                p1MatchScore += DEFECTOR_SCORE;
                p2MatchScore += COOPERATOR_SCORE;
            } else { // Cả hai cùng phản bội (DEFECT)
                p1MatchScore += BOTH_DEFECT_SCORE;
                p2MatchScore += BOTH_DEFECT_SCORE;
            }

            // Lưu lại nước đi cho vòng tiếp theo
            p1LastMove = p1Move;
            p2LastMove = p2Move;
        }

        // Cập nhật tổng điểm
        totalScores.put(p1.getName(), totalScores.get(p1.getName()) + p1MatchScore);

        // Nếu không phải là tự đấu với chính mình, cập nhật điểm cho p2
        if (p1 != p2) {
            totalScores.put(p2.getName(), totalScores.get(p2.getName()) + p2MatchScore);
        }

        // System.out.printf("Trận đấu: %s (%d) vs %s (%d)\n",
        //         p1.getName(), p1MatchScore, p2.getName(), p2MatchScore);
    }

    /**
     * In kết quả cuối cùng, nhóm theo LOẠI chiến lược
     * và tính điểm trung bình cho mỗi bản sao.
     * (ĐÃ CĂN CHỈNH CỘT)
     */
    public void printResults() {
        System.out.println("--------------------------------------------------");
        System.out.println("Kết quả giải đấu (Tổng hợp theo loại):");
        System.out.printf("(Tổng cộng %d bản sao chiến lược tham gia)\n", strategies.size());
        System.out.println("--------------------------------------------------");

        // Map để tổng hợp điểm và đếm số lượng cho từng LOẠI chiến lược
        Map<String, StrategyResult> aggregateResults = new HashMap<>();

        // Lặp qua tất cả các điểm số CÁ NHÂN (ví dụ: "Tit for Tat #1", "Tit for Tat #2")
        for (Map.Entry<String, Integer> entry : totalScores.entrySet()) {
            String uniqueName = entry.getKey();
            int score = entry.getValue();

            // Tách tên gốc ra (ví dụ: "Tit for Tat #2" -> "Tit for Tat")
            String baseName = uniqueName.split(" #")[0];

            // Lấy hoặc tạo mới đối tượng StrategyResult cho tên gốc
            StrategyResult result = aggregateResults.computeIfAbsent(baseName, StrategyKey -> new StrategyResult(baseName)); // (Đã sửa lỗi cú pháp new)

            // Cộng dồn điểm và tăng số đếm
            result.count++;
            result.totalScore += score;
        }

        // Tính điểm trung bình cho mỗi loại và chuyển sang một List để sắp xếp
        List<StrategyResult> sortedList = new ArrayList<>(aggregateResults.values());
        for (StrategyResult result : sortedList) {
            if (result.count > 0) {
                result.averageScore = (double) result.totalScore / result.count;
            }
        }

        // Sắp xếp danh sách theo Điểm TB/Bản sao, từ cao đến thấp
        // (Sử dụng phiên bản Java 7-compatible để đảm bảo hoạt động)
        Collections.sort(sortedList, new Comparator<StrategyResult>() {
            @Override
            public int compare(StrategyResult r1, StrategyResult r2) {
                return Double.compare(r2.averageScore, r1.averageScore);
            }
        });

        // --- PHẦN SỬA ĐỔI CHÍNH ---
        // Tăng độ rộng cột 2 (từ 30 -> 35) và cột 3 (từ 10 -> 20)

        // In tiêu đề cột
        System.out.printf("%-4s %-35s %-20s %-12s %-15s\n",
                "Hạng", "Loại tính cách", "Số lượng (người)", "Tổng điểm", "Tổng điểm/Người");

        // In đường kẻ phân cách
        System.out.printf("%-4s %-35s %-20s %-12s %-15s\n",
                "----", "-----------------------------------", "--------------------", "------------", "---------------");

        // In kết quả
        for (int i = 0; i < sortedList.size(); i++) {
            StrategyResult result = sortedList.get(i);

            // Dùng cùng định dạng với tiêu đề
            System.out.printf("%-4d %-35s %-20d %-12d %-15.2f\n",
                    (i + 1),
                    result.name,
                    result.count,
                    result.totalScore,
                    result.averageScore);
        }
    }

    /**
     * Phương thức main để chạy chương trình.
     */
    public static void main(String[] args) {
        PrisonersDilemmaTournament tournament = new PrisonersDilemmaTournament();
        tournament.addStrategies();
        tournament.runTournament();
        tournament.printResults();
    }
}