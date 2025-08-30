/*
 * Decompiled with CFR 0.152.
 */
package elucent.rootsclassic.research;

import elucent.rootsclassic.research.ResearchBase;
import java.util.ArrayList;
import java.util.List;

public class ResearchGroup {
    private String name = "";
    private String properName = "";
    public List<ResearchBase> researches = new ArrayList<ResearchBase>();

    public ResearchGroup(String name, String properName) {
        this.name = name;
        this.properName = properName;
    }

    public ResearchGroup addResearch(ResearchBase research) {
        if (!research.getInfo().isEmpty()) {
            this.researches.add(research);
        }
        return this;
    }

    public String getName() {
        return this.name;
    }

    public String getProperName() {
        return this.properName;
    }
}

