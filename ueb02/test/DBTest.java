import db.DB;
import db.DBTable;
import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;

import static org.junit.Assert.*;

public class DBTest {
    DB test1 = new DB("test1");

    String[] IDS_1 = {"S1", "S2"};
    String[] IDS_2 = {"P1", "P2"};


    DBTable tab1 = new DBTable("tab1", new LinkedHashSet<String>(Arrays.asList(IDS_1)));
    DBTable tab2 = new DBTable("tab2", new LinkedHashSet<String>(Arrays.asList(IDS_2)));


    /**
     * Liefert die Stringrepräsentation der Datenbank. Die Stringrepräsentation ist wie folgt
     * aufgebaut:
     * <ul>
     * <li>In der ersten Zeile steht <code>Datenbankname: </code> gefolgt von dem Bezeichner der
     * Datenbank.</li>
     * <li>Die zweite Zeile ist eine Leerzeile.</li>
     * <li>Es folgen Datenbanktabellen in aufsteigender Reihenfolge ihrer Bezeichner in folgender
     * Form:
     * <ul>
     * <li>Vor jeder Tabelle steht <code>Tabellenname: </code> gefolgt von dem Bezeichner der
     * Datenbanktabelle.</li>
     * <li>Danach folgt eine Leerzeile.</li>
     * <li>Es folgt die Stringrepräsentation der Datenbanktabelle.</li>
     * </ul>
     * </li>
     * <li>Nach jeder Tabelle folgt eine Leerzeile.</li>
     * </ul>
     *
     * @return die Stringrepräsentation der Datenbank.
     */
    @Test
    public void addTableTest() {

        test1.addTable(tab1);
        test1.addTable(tab2);
        assertEquals(2, test1.getTableCnt());
        String exp = "Datenbankname: test1\n\nTabellenname: tab1\n\n" + tab1.toString() + "\nTabellenname: tab2\n\n" + tab2.toString() + "\n";
        String s = test1.toString();
        assertTrue(exp.equals(s));

        test1.removeAllTables();
    }

    @Test
    public void removeTableTest() {
        test1.addTable(tab1);
        test1.removeTable("tab1");
        assertEquals(0, test1.getTableCnt());
    }

    @Test(expected = AssertionError.class)
    public void removeTableTestInvalid() {
        test1.removeTable("_tab2");
    }

    @Test
    public void removeTableTestNone() {
        test1.addTable(tab1);
        test1.removeTable("tab2");
        assertEquals(1, test1.getTableCnt());
        test1.removeAllTables();
    }


    @Test
    public void getTableIdsTest() {
        test1.addTable(tab2);
        test1.addTable(tab1);
        List<String> res = test1.getTableIds();
        assertEquals(2, res.size());
        assertTrue(res.get(0).equals("tab1"));
        assertTrue(res.get(1).equals("tab2"));
        test1.removeAllTables();

    }

    @Test
    public void tableExistsTest() {
        test1.addTable(tab2);
        test1.addTable(tab1);
        assertTrue(test1.tableExists("tab1"));
        assertTrue(test1.tableExists("tab2"));
        assertFalse(test1.tableExists("tab3"));
        test1.removeAllTables();
    }

}