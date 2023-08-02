import db.DBTable;
import db.SortDirection;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;

import static org.junit.Assert.*;

public class DBTableTest {
    String[] IDS = {"S_1", "S_2", "S_3"};
    String[] IDS_2 = {"S1", "S2"};


    DBTable tab = new DBTable("tab", new LinkedHashSet<String>(Arrays.asList(IDS)));
    DBTable tab2 = new DBTable("tab2", new LinkedHashSet<String>(Arrays.asList(IDS_2)));

    @Test
    public void hasCol() {
        assertFalse("gefüllte tabelle false", tab.hasCol("S_4"));
        assertTrue("gefüllte tabelle true", tab.hasCol("S_2"));
    }

    @Test(expected = AssertionError.class)
    public void hasColNullError() {
        boolean b = tab.hasCol(null);
    }


    @Test(expected = AssertionError.class)
    public void hasColInvalidIdError() {
        boolean b = tab.hasCol(")esarfjh4");
    }


    @Test
    public void hasCols() {
        assertTrue("tab hasCols alle", tab.hasCols(Arrays.asList(IDS)));
        assertTrue("tab hasCols einige", tab.hasCols(Arrays.asList("S_3", "S_1")));
        assertFalse("tab hasCols false", tab.hasCols(Arrays.asList("S_1", "S_3", "S_2", "S_4")));
    }

    @Test
    public void getColCnt() {
        assertEquals(3, tab.getColCnt());
    }

    @Test
    public void getRowCnt() {
        assertEquals(0, tab.getRowCnt());
        tab.appendRow(Arrays.asList(IDS));
        assertEquals(1, tab.getRowCnt());
        tab.removeAllRows();
        assertEquals(0, tab.getRowCnt());
    }

    @Test(expected = AssertionError.class)
    public void appendRowError() {
        tab.appendRow(Arrays.asList("Adfgvds", "adads", "sgfsgf", "sfsefs"));
    }

    @Test
    public void getColIds() {
        assertArrayEquals(IDS, tab.getColIds().toArray());
    }


    /**
     * Liefert die Stringrepräsentation der Datenbanktabelle. Die Stringrepräsentation ist wie folgt
     * aufgebaut:
     * <ul>
     * <li>In der ersten Zeile stehen durch Kommata getrennt die Bezeichner der Spalten der
     * Datenbanktabelle.</li>
     * <li>Es folgen die Zeilen der Datenbanktabelle in der Reihenfolge, in der sie in der
     * Datenbanktabelle gespeichert sind. Die einzelnen Werte einer Zeile der Datenbanktabelle
     * werden durch Kommata getrennt hintereinander gereiht.</li>
     * </ul>
     * <p>
     * Zeilenumbrüche (Unix, DOS, MacOs) innerhalb der Felder der Tabelle werden zugunsten der
     * besseren Lesbarkeit hierbei entfernt.
     * <p>
     * Es werden stets Unix-Zeilenumbrüche (\n) verwendet.
     *
     * @return Stringrepräsentation der Datenbanktabelle.
     */

    @Test
    public void sort() {
        ArrayList<String> r1 = new ArrayList<>(Arrays.asList("k", "h"));
        ArrayList<String> r2 = new ArrayList<>(Arrays.asList("f", "x"));
        ArrayList<String> r3 = new ArrayList<>(Arrays.asList("i", "a"));
        tab2.appendRow(r1);
        tab2.appendRow(r2);
        tab2.appendRow(r3);

        tab2.sort("S1", SortDirection.ASC);
        String exp = "S1,S2\nf,x\ni,a\nk,h\n";
        String s = tab2.toString();
        assertTrue("Aufsteigend", exp.equals(s));

        tab2.sort("S2", SortDirection.DESC);
        exp = "S1,S2\nf,x\nk,h\ni,a\n";
        s = tab2.toString();
        assertTrue("Absteigend", exp.equals(s));

        tab2.removeAllRows();

    }

    @Test(expected = AssertionError.class)
    public void sortError() {
        ArrayList<String> r1 = new ArrayList<>(Arrays.asList("k", "h"));
        ArrayList<String> r2 = new ArrayList<>(Arrays.asList("f", "x"));
        ArrayList<String> r3 = new ArrayList<>(Arrays.asList("i", "a"));
        tab2.appendRow(r1);
        tab2.appendRow(r2);
        tab2.appendRow(r3);

        tab2.sort("S3", SortDirection.ASC);
        tab2.removeAllRows();

    }

    @Test
    public void removeRows() {
        ArrayList<String> r1 = new ArrayList<>(Arrays.asList("k", "h"));
        ArrayList<String> r2 = new ArrayList<>(Arrays.asList("f", "x"));
        ArrayList<String> r3 = new ArrayList<>(Arrays.asList("i", "a"));
        ArrayList<String> r4 = new ArrayList<>(Arrays.asList("i", "f"));
        ArrayList<String> r5 = new ArrayList<>(Arrays.asList("f", "p"));
        tab2.appendRow(r1);
        tab2.appendRow(r2);
        tab2.appendRow(r3);
        tab2.appendRow(r4);
        tab2.appendRow(r5);

        tab2.removeRows("S1", new EqualsPredicate("f"));
        String exp = "S1,S2\nk,h\ni,a\ni,f\n";
        String s = tab2.toString();
        assertTrue(exp.equals(s));
        tab2.removeAllRows();
    }

    @Test
    public void select() {
        ArrayList<String> r1 = new ArrayList<>(Arrays.asList("k", "h"));
        ArrayList<String> r2 = new ArrayList<>(Arrays.asList("f", "x"));
        ArrayList<String> r3 = new ArrayList<>(Arrays.asList("i", "a"));
        ArrayList<String> r4 = new ArrayList<>(Arrays.asList("a", "f"));
        ArrayList<String> r5 = new ArrayList<>(Arrays.asList("f", "a"));
        tab2.appendRow(r1);
        tab2.appendRow(r2);
        tab2.appendRow(r3);
        tab2.appendRow(r4);
        tab2.appendRow(r5);

        DBTable newTab = tab2.select("S2", new EqualsPredicate("a"), "res");
        String exp = "S1,S2\ni,a\nf,a\n";
        String s = newTab.toString();
        assertTrue(exp.equals(s));
        tab2.removeAllRows();
    }

    @Test
    public void selectEmptyResult() {
        ArrayList<String> r1 = new ArrayList<>(Arrays.asList("k", "h"));
        ArrayList<String> r2 = new ArrayList<>(Arrays.asList("f", "x"));
        ArrayList<String> r3 = new ArrayList<>(Arrays.asList("i", "b"));
        ArrayList<String> r4 = new ArrayList<>(Arrays.asList("a", "f"));
        ArrayList<String> r5 = new ArrayList<>(Arrays.asList("f", "b"));
        tab2.appendRow(r1);
        tab2.appendRow(r2);
        tab2.appendRow(r3);
        tab2.appendRow(r4);
        tab2.appendRow(r5);

        DBTable newTab = tab2.select("S2", new EqualsPredicate("a"), "res");
        String exp = "S1,S2\n";
        String s = newTab.toString();
        assertTrue(exp.equals(s));
        tab2.removeAllRows();
    }

    @Test
    public void project() {
        ArrayList<String> r1 = new ArrayList<>(Arrays.asList("k", "h", "j"));
        ArrayList<String> r2 = new ArrayList<>(Arrays.asList("f", "x", "m"));
        ArrayList<String> r3 = new ArrayList<>(Arrays.asList("i", "b", "l"));

        tab.appendRow(r1);
        tab.appendRow(r2);
        tab.appendRow(r3);

        DBTable newTab = tab.project(new LinkedHashSet<>(Arrays.asList("S_1", "S_3")), "newTab");
        String exp = "S_1,S_3\nk,j\nf,m\ni,l\n";
        String s = newTab.toString();
        assertTrue(exp.equals(s));
        tab.removeAllRows();
    }

    @Test
    public void project2() {
        ArrayList<String> r1 = new ArrayList<>(Arrays.asList("k", "h", "j"));
        ArrayList<String> r2 = new ArrayList<>(Arrays.asList("f", "x", "m"));
        ArrayList<String> r3 = new ArrayList<>(Arrays.asList("i", "b", "l"));

        tab.appendRow(r1);
        tab.appendRow(r2);
        tab.appendRow(r3);

        DBTable newTab = tab.project(new LinkedHashSet<>(Arrays.asList("S_1", "S_1")), "newTab");
        String exp = "S_1\nk\nf\ni\n";
        String s = newTab.toString();
        assertTrue(exp.equals(s));
        tab.removeAllRows();
    }


    @Test
    public void equijoin() {
        ArrayList<String> r11 = new ArrayList<>(Arrays.asList("k", "h", "j"));
        ArrayList<String> r12 = new ArrayList<>(Arrays.asList("f", "x", "m"));
        ArrayList<String> r13 = new ArrayList<>(Arrays.asList("i", "b", "l"));
        tab.appendRow(r11);
        tab.appendRow(r12);
        tab.appendRow(r13);

        ArrayList<String> r21 = new ArrayList<>(Arrays.asList("k", "h"));
        ArrayList<String> r22 = new ArrayList<>(Arrays.asList("f", "m"));
        ArrayList<String> r23 = new ArrayList<>(Arrays.asList("h", "a"));
        ArrayList<String> r24 = new ArrayList<>(Arrays.asList("a", "h"));
        ArrayList<String> r25 = new ArrayList<>(Arrays.asList("f", "a"));
        tab2.appendRow(r21);
        tab2.appendRow(r22);
        tab2.appendRow(r23);
        tab2.appendRow(r24);
        tab2.appendRow(r25);

        DBTable res = tab.equijoin(tab2, "S_2", "S2", "res");
        String exp = "tab_S_1,tab_S_2,tab_S_3,tab2_S1,tab2_S2\nk,h,j,k,h\nk,h,j,a,h\n";
        String s = res.toString();
        assertTrue(exp.equals(s));

        tab.removeAllRows();
        tab2.removeAllRows();
    }

    @Test
    public void equijoinEmptyResult() {
        ArrayList<String> r11 = new ArrayList<>(Arrays.asList("k", "h", "j"));
        ArrayList<String> r12 = new ArrayList<>(Arrays.asList("f", "x", "m"));
        ArrayList<String> r13 = new ArrayList<>(Arrays.asList("i", "b", "l"));
        tab.appendRow(r11);
        tab.appendRow(r12);
        tab.appendRow(r13);

        ArrayList<String> r21 = new ArrayList<>(Arrays.asList("k", "m"));
        ArrayList<String> r22 = new ArrayList<>(Arrays.asList("f", "m"));
        ArrayList<String> r23 = new ArrayList<>(Arrays.asList("h", "m"));
        ArrayList<String> r24 = new ArrayList<>(Arrays.asList("a", "l"));
        ArrayList<String> r25 = new ArrayList<>(Arrays.asList("f", "a"));
        tab2.appendRow(r21);
        tab2.appendRow(r22);
        tab2.appendRow(r23);
        tab2.appendRow(r24);
        tab2.appendRow(r25);

        DBTable res = tab.equijoin(tab2, "S_2", "S2", "res");
        String exp = "tab_S_1,tab_S_2,tab_S_3,tab2_S1,tab2_S2\n";
        String s = res.toString();
        assertTrue(exp.equals(s));

        tab.removeAllRows();
        tab2.removeAllRows();
    }

    @Test
    public void isValidIdentifier() {
        StringBuilder sb = new StringBuilder();
        assertTrue(DBTable.isValidIdentifier("Abc_19ab"));
        assertTrue(DBTable.isValidIdentifier("a_AG2347__"));
        assertFalse(DBTable.isValidIdentifier("9_128dcfdf"));
        assertFalse(DBTable.isValidIdentifier("A_-kiu"));
        assertFalse(DBTable.isValidIdentifier(sb.toString()));
        assertFalse(DBTable.isValidIdentifier(""));
    }

    @Test
    public void areOnlyUniqueValuesTest() {
        ArrayList<String> values1 = new ArrayList<>(Arrays.asList("Baum", "Haus", "Test"));
        ArrayList<String> values2 = new ArrayList<>(Arrays.asList("Baum", "Haus", "Test", "Haus"));
        ArrayList<String> values3 = new ArrayList<>(Arrays.asList("Baum", "Ha'us", "Test", "Haus"));

        assertTrue(DBTable.areOnlyUniqueValues(values1));
        assertFalse(DBTable.areOnlyUniqueValues(values2));
        assertTrue(DBTable.areValidIdentifiers(values1));
        assertTrue(DBTable.areValidIdentifiers(values2));
        assertFalse(DBTable.areValidIdentifiers(values3));
    }

}