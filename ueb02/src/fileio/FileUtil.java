package fileio;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PushbackReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import db.DBTable;

/**
 * Bietet Methoden für das Lesen und Schreiben von {@link DBTable}s aus bzw. in Dateien.
 *
 * @author kar, mhe
 */
public class FileUtil {

    /**
     * Konstante zur Signalisierung des Endes einer Datei
     */
    private static final int EOF = -1;


    /**
     * prueft, ob es sich beim uebergebenen Zeichen um einen Zeilenumbruch handelt
     *
     * @param chr ASCII-Wert des Zeichens, welches ueberprueft wird
     * @return true, wenn es sich um einen Zeilenumbruch handelt
     */
    private boolean isLineFeed(int chr) {
        return chr == '\r' || chr == '\n';
    }

    /**
     * Liest eine Text-Datei und interpretiert diese als {@link DBTable}. Dabei wird das Quoting
     * innerhalb der einzelnen Felder wieder rückgängig gemacht, so dass sich die Inhalte der
     * Tabelle wieder im Originalzustand befinden. (siehe
     * {@link FileUtil#writeTableToFile(String, DBTable)}).
     * <p>
     * Syntaktische Fehler in der Datei werden dabei über eine WrongSyntaxException signalisiert. Zu
     * den syntaktischen Fehlern zählen hierbei auch nicht valide Bezeichner und die Verletzung der
     * Konsistenzbedingungen für Bezeichner der Datenbank (also verletzte Vorbedingungen).
     *
     * @param filename Dateiname
     * @return Tabelle als DBTable
     * @throws IOException          Fehler beim Einlesen der Datei
     * @throws WrongSyntaxException Fehler in der Syntax der Eingabedatei
     * @pre filename != null
     */
    public DBTable readTableFromFile(final String filename) throws IOException,
            WrongSyntaxException {
        assert filename != null;

        File f = new File(Paths.get(filename).toString());
        FileReader reader = null;
        reader = new FileReader(f);
        PushbackReader pReader = new PushbackReader(reader);
        int c = EOF;
        if (pReader.ready()) {
            StringBuilder id = new StringBuilder();
            ArrayList<ArrayList<String>> rows = new ArrayList<>();
            ArrayList<String> head = new ArrayList<>();
            ArrayList<String> row = new ArrayList<>();
            c = pReader.read();
            //Tabellenbezeichner einlesen
            while (!isLineFeed(c)) {
                if (c == EOF) {
                    throw new WrongSyntaxException("Unvollständige Datei");
                }
                id.append((char) c);
                c = pReader.read();
            }
            if (!DBTable.isValidIdentifier(id.toString())) {
                throw new WrongSyntaxException("Ungültiger Tabellenbezeichner");
            }

            //Spaltenbezeichner einlesen -> head
            int colCnt = 0;
            if (c == '\r') { //Zeilenumbruch durch "\r\n" behandeln
                c = pReader.read();
                if (c != '\n') {
                    pReader.unread(c);
                }
            }
            c = pReader.read();
            StringBuilder sb = new StringBuilder();
            while (!isLineFeed(c)) {
                if (c == EOF) {
                    throw new WrongSyntaxException("Unvollständige Datei");
                }
                if (c == ',') { //Komma trent einzelne Eintraege
                    head.add(sb.toString());
                    sb = new StringBuilder();
                    colCnt++;
                } else {
                    sb.append((char) c);
                }
                c = pReader.read();
            }

            head.add(sb.toString());
            colCnt++;

            if (!DBTable.areValidIdentifiers(head)) {
                throw new WrongSyntaxException("Ungültiger Spaltenbezeichner");
            }
            if (!DBTable.areOnlyUniqueValues(head)) {
                throw new WrongSyntaxException("Mehrfacher Spaltenbezeichner");
            }


            //Tabelleninhalt einlesen
            sb = new StringBuilder();
            if (c == '\r') { //Zeilenumbruch durch "\r\n" behandeln
                c = pReader.read();
                if (c != '\n') {
                    pReader.unread(c);
                }
            }
            c = pReader.read();
            int wordCnt = 0;
            while (c != EOF) {
                if (c == '\\') { //
                    c = pReader.read();
                    //Backslash vor Zeichen, welches nicht gequotet wird
                    if ((c != ',') && (c != '\\')) {
                        throw new WrongSyntaxException("Fehlerhaftes Quoting");
                    //Backslash vor korrekt gequotetem Zeichen
                    } else {
                        sb.append((char) c);
                    }
                } else if (c == ',') { //Komma, das Tabelleneintraege trennt
                    wordCnt++;
                    row.add(sb.toString());
                    sb = new StringBuilder();
                    //Eine Zeile eingelesen
                    if (wordCnt % colCnt == 0) {
                        rows.add(new ArrayList<>(row));
                        row.clear();
                    }
                } else {
                    sb.append((char) c);
                }
                c = pReader.read();
            }
            if (sb.length() != 0) {
                row.add(sb.toString());
                rows.add(row);
            }

            //DBTable erstellen mit den Werten
            DBTable res = new DBTable(id.toString(), head);
            for (ArrayList<String> r : rows) {
                try {
                    res.appendRow(r);
                } catch (AssertionError ae) {
                    throw new WrongSyntaxException("Fehler beim Zeile hinzufuegen");
                }
            }
            pReader.close();
            return res;
        } else {
            pReader.close();
            throw new IOException("Datei kann nicht gelesen werden");
        }
    }

    /**
     * Schreibt die {@link DBTable} in die Datei namens filename. Die Syntax und Semantik der
     * Ausgabe sind identisch zu {@link DBTable#toFile()}.
     *
     * @param filename Dateiname
     * @param table    Tabelle, welche geschrieben werden soll
     * @throws IOException Fehler beim Schreiben der Datei
     * @pre filename != null
     * @pre table != null
     */
    public void writeTableToFile(final String filename, final DBTable table)
            throws IOException {
        assert filename != null;
        assert table != null;
        BufferedWriter writer = Files.newBufferedWriter(Paths.get(filename));
        writer.write(table.toFile());
        writer.close();
    }

}
