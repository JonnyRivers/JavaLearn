import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FPLStatic {
    @SerializedName("elements")
    public List<FPLElement> Elements;
    @SerializedName("teams")
    public List<FPLTeam> Teams;
}
