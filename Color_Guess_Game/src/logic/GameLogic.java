package logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameLogic {
    private final String[] availableColors = {"RED", "GREEN", "BLUE", "YELLOW", "ORANGE", "PURPLE"};
    private final String[] secretColors = new String[4]; // สีลับที่ต้องทาย

    public GameLogic() {
        generateSecretColors();
    }

    // สุ่มสีลับ
    public void generateSecretColors() {
        List<String> colorList = new ArrayList<>();
        Collections.addAll(colorList, availableColors);
        for (int i = 0; i < 4; i++) {
            secretColors[i] = colorList.get((int) (Math.random() * colorList.size()));
        }
        System.out.println("Secret Colors: " + String.join(", ", secretColors)); // Debugging
    }

	public int[] checkColors(String[] guessedColors) {
        if (guessedColors == null || guessedColors.length != 4) {
            throw new IllegalArgumentException("Guessed colors must have exactly 4 elements.");
        }

        int correctPositionAndColor = 0; // สีและตำแหน่งถูกต้อง
        int correctColorOnly = 0;       // สีถูกต้องแต่ตำแหน่งผิด
        boolean[] secretUsed = new boolean[4]; // เก็บสถานะของสีใน secret ว่าใช้ไปแล้วหรือยัง
        boolean[] guessUsed = new boolean[4];  // เก็บสถานะของสีที่ทายว่าใช้ไปแล้วหรือยัง

        // ตรวจสอบสีและตำแหน่งที่ถูกต้องก่อน
        for (int i = 0; i < 4; i++) {
            if (guessedColors[i].equals(secretColors[i])) {
                correctPositionAndColor++;
                secretUsed[i] = true;
                guessUsed[i] = true;
            }
        }

        // ตรวจสอบเฉพาะสีที่ถูกต้องแต่ตำแหน่งผิด
        for (int i = 0; i < 4; i++) {
            if (!guessUsed[i]) { // ถ้าสียังไม่ได้ถูกนับ
                for (int j = 0; j < 4; j++) {
                    if (!secretUsed[j] && guessedColors[i].equals(secretColors[j])) {
                        correctColorOnly++;
                        secretUsed[j] = true;
                        break;
                    }
                }
            }
        }

        return new int[]{correctPositionAndColor, correctColorOnly};
    }

    public String[] getSecretColors() {
        return secretColors;
    }

	public String[] getAvailableColors() {
		return availableColors;
	}
   
}
