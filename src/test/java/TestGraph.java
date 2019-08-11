import com.company.Main;
import com.company.MaxFlow;
import org.junit.Test;

import java.util.Scanner;

public class TestGraph {
    @Test
    public void testDfs() throws Exception {
        Main.createNewGraph(new Scanner("4\n" + "5\n" +
                "0\n" + "1\n" + "3\n" +
                "1\n" + "2\n" + "1\n" +
                "0\n" + "2\n" + "1\n" +
                "1\n" + "3\n" + "1\n" +
                "2\n" + "3\n" + "3\n"));
        System.out.println(Main.dfs());
    }


    @Test
    public void testDfs2() throws Exception {
        Main.createNewGraph(new Scanner("6\n" + "8\n" +
                "0\n" + "1\n" + "11\n" +
                "0\n" + "2\n" + "12\n" +
                "1\n" + "3\n" + "12\n" +
                "2\n" + "1\n" + "1\n" +
                "2\n" + "4\n" + "11\n"+
                "4\n" + "3\n" + "7\n" +
                "4\n" + "5\n" + "4\n" +
                "3\n" + "5\n" + "19\n" ));
        System.out.println(Main.dfs());
    }

    @Test
    public void testDfs3() throws Exception {
        MaxFlow.start(new Scanner("4\n" + "5\n" +
                "0\n" + "1\n" + "3\n" +
                "1\n" + "2\n" + "1\n" +
                "0\n" + "2\n" + "1\n" +
                "1\n" + "3\n" + "1\n" +
                "2\n" + "3\n" + "3\n"));
    }
}
