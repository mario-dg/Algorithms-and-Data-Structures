import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/* Angabe der Test-Klassen die zu dieser Test-Suite gehören sollen: */
@Suite.SuiteClasses({

        TestDBEmpty.class,
        TestDBTableEmpty.class,
        TestDBOneTable.class,
        TestDBTableOneRow.class,

        TestFilesDBTable.class,
        DBTableTest.class,
        DBTest.class

})

/* Tests über eine Test-Suite ausführen */
@RunWith(Suite.class)
/* Die eigentliche Test-Suite-Klasse für JUnit */
public class TestSuite {

    /* Methode zum einfachen Starten der Test-Suite auch per Kommandozeile. */
    public static void main(String[] args) {
        org.junit.runner.JUnitCore.main(TestSuite.class.getName());
    }

}
