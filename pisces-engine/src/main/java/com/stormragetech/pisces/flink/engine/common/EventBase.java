package com.stormragetech.pisces.flink.engine.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventBase implements Serializable {

    private static final long serialVersionUID = 8699576947483067518L;

    //案件编号
    private String eventId;
    //案卷号
    private String eventCode;
    //事件主题ID
    private String eventSubjectId;
    //事件类型
    private String eventTypeId;
    //事件名称
    private String eventName;
    //事件状态
    private String eventStatusId;
    //案件描述
    private String eventDesc;
    //案件位置描述
    private String eventPosDesc;
    //事件级别
    private String eventLevelId;
    //事件破坏级别
    private String eventDamgLevelId;
    //事件发生时间
    private String eventOccurTime;

    //网格ID
    private String eventGridId;
    //坐标类型
    private String eventCoordinateType;
    //x坐标
    private String eventPosX;
    //
    private String eventPosY;
    //原x坐标
    private String eventOriPosX;
    //原Y坐标
    private String eventOriPosY;
    //当前处置部门
    private String eventRepDepartId;
    //处置部门名称
    private String eventRepDepartName;

    //附件ID
    private String eventAttachId;
    //附件列表
    private String eventAttachList;

    //督办次数
    private int eventOverseeTimes;
    //上报类型
    private String rptTypeId;
    //上报渠道
    private String rptChannelId;
    //上报人员ID
    private String rptUserId;
    //上报人名称
    private String rptUserName;
    //上报时间
    private String rptTime;
    //事件登记人ID
    private String reportUserId;
    //事件登记人姓名
    private String reportUserName;

    private String orgId;

    private String orgName;
    //
    private String keyWord;
    //最后操作时间
    private String lastModifyTime;
    //
    private String parentEventId;
    //检查单位ID
    private String objectId;

    private String objectName;
    //
    private String eventTag;

    private String eventTagName;

    //流程模块ID
    private String wfDeployId;
    //流程ID
    private String wfProcessId;

    //主流程模块ID
    private String mainWfDeployId;
    //主流程ID
    private String mainWfProcessId;
    //附件字符串
    private String attachStr;
    //业务操作类型
    private String eventBizTypeId;

    private String eventBizTypeName;
    //主题名称
    private String eventSubjectName;
    //事件类型名称
    private String eventTypeName;
    //状态名称
    private String eventStatusName;
    //事件基本名称
    private String eventLevelName;
    //事件破坏基本名称
    private String eventDamgLevelName;
    //网格名称
    private String eventGridName;
    //上报方式名称
    private String rptTypeName;
    //上报渠道类型
    private String rptChannelName;
    //操作人部门
    private String opDepartId;

    private String opDepartName;
    //流程节点ID
    private String pTaskId;
    //操作人ID
    private String opUserId;

    //事件上报平台ID
    private String eventPlatformId;
    //事件上报平台名称
    private String eventPlatformName;

    private String delayDemandDeadline;
    //一单多派的主责部门
    private String eventMainDepartId;
    //处置时限
    private String eventdispDemandDeadline;
    //响应时限
    private String eventdispResponseDeadline;

    private String outerEventId;

    private String posGeometry;

    private String eventCoordidateType;

    private String extInfo;

}
