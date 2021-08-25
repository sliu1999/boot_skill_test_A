package com.sliu.domain;


import lombok.Data;
import javax.xml.bind.annotation.XmlElement;
import java.util.Date;


 
@Data
public class TbPartyLearnStatistics{



    private Long partyHeadquartersId;
                /*组织名称*/
	@XmlElement(name="组织")

    private String organizationName;
            /*活跃成员*/
	@XmlElement(name="活跃学员")

    private Integer activeMember;
            /*学员总数*/
	@XmlElement(name="学员总数")

    private Integer totalMember;
            /*总获得积分*/
	@XmlElement(name="总获得积分")

    private Double totalScore;
            /*党员填写率*/
	@XmlElement(name="是否党员填写率")

    private String fillRate;
            /*参与度*/
	@XmlElement(name="参与度")

    private String degree;
            /*当日人均积分*/
	@XmlElement(name="当日人均积分")

    private Double perCapitaIntegral;
            /*日期*/
	@XmlElement(name="日期")
    private Date date;
                                        	

}
