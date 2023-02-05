import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FPLStatic {
    @SerializedName("total_players")
    public int TotalPlayers;
    @SerializedName("elements")
    public List<FPLElement> Elements;
}
