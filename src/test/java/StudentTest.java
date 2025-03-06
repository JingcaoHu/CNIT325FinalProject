import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import main.Student;

public class StudentTest {
    @Test
    // Test必须要测试所有算法的boundary
    public void StudentsTest(){
        Student student1 = new Student("Kyle",1);
        
        String expectOutput = "Kyle 1";
        String actualOutput = student1.toString();
        assertEquals(expectOutput, actualOutput);
    }

}
