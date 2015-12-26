package om.ceitechs.duperaser.domain;

import lombok.Getter;
import lombok.Setter;
import om.ceitechs.duperaser.PatternExtractorUtility;

/**
 * Created by iddymagohe on 10/28/15.
 */
@Getter @Setter
public class ConstituencyEntity {
    private String id;
    private String name;
    private long totalVotes;
    private long ccmVotes;
    private long cmdVotes;

    public ConstituencyEntity(ConstituencyResult fromresult) {
        this.id = fromresult.getId();
        this.name = PatternExtractorUtility.extractConstituencyName(fromresult.getText());
        this.totalVotes = PatternExtractorUtility.extractTotalVotes(fromresult.getText());
        this.ccmVotes = PatternExtractorUtility.extractCCMTotalVotes(fromresult.getText());
        this.cmdVotes = PatternExtractorUtility.extractCMDTotalVotes(fromresult.getText());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConstituencyEntity that = (ConstituencyEntity) o;

        if (totalVotes != that.totalVotes) return false;
        if (ccmVotes != that.ccmVotes) return false;
        if (cmdVotes != that.cmdVotes) return false;
        return name.equals(that.name);

    }

    public boolean secondaryEquals(ConstituencyEntity other){
        return !equals(other) && getName().equalsIgnoreCase(other.getName());
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + (int) (totalVotes ^ (totalVotes >>> 32));
        result = 31 * result + (int) (ccmVotes ^ (ccmVotes >>> 32));
        result = 31 * result + (int) (cmdVotes ^ (cmdVotes >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "ConstituencyEntity{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", totalVotes=" + totalVotes +
                ", ccmVotes=" + ccmVotes +
                ", cmdVotes=" + cmdVotes +
                '}';
    }


}
