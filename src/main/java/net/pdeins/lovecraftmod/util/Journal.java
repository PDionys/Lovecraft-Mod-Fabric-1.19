package net.pdeins.lovecraftmod.util;

public class Journal {
    private final String progress;
    private final String journalLine;

    public Journal(String progress, String journalLine){
        this.progress = progress;
        this.journalLine = journalLine;
    }

    public String getProgress(){
        return progress;
    }

    public String getJournalLine() {
        return journalLine;
    }
}
