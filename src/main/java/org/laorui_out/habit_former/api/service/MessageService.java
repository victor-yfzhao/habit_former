package org.laorui_out.habit_former.api.service;

import org.springframework.stereotype.Service;

@Service
public class MessageService {
    //日程安排prompt模板
    //不能有换行符！不然会报很奇怪的错
    private static String PLANNER_PROMPT="能力与角色:我希望你是一位熟悉{theme}领域的专家 " +
            "背景信息:我正在计划实现{target}，希望能够通过计划日程来培养习惯，实现这个目标 " +
            "指令:我希望你能为我规划一个{time}天的{theme}主题的日程计划，需要实现的目标是{target} " +
            "指令合法性检验：注意！如果觉得{target}与{theme}主题无关，请忽略后续的文字，然后返回,且只需要返回一个“error not-related”的字符串，不需要其他的任何输出！否则就忽略合法性检验。 " +
            "输出风格::我希望你能够将输出封装为一个名为'task'的JSON格式的数组，'task'中的每个元素代表一个任务项。每个任务项包含 表示第几天的int型属性'day',string型属性任务名'task_name',string型属性具体任务内容'task_content'。除了JSON数据的输出，不要包含任何其他文本。 " +
            "输出范围:我希望你能够以详细的任务执行内容来回答我，按照每一天具体到第几天、对应的任务项（包括任务名和具体任务内容）来安排日程，要求你的回答按照时间来排序。 ";
    private static final String PLANNER_PROMPT_STUDY="能力与角色:我希望你是一位熟悉学习教育领域的专家 " +
            "背景信息:我正在计划实现{target}，希望能够通过计划日程来培养习惯，实现这个目标  " +
            "指令:我希望你能为我规划一个{time}天的学习教育主题的日程计划，需要实现的目标是{target} " +
            "输出风格:我希望你能够将输出封装为一个JSON格式的数组，数组中的每个元素代表一个任务项。每个任务项包含 表示第几天的int型属性'day',string型属性任务名'task_name',string型属性具体任务内容'task_content',string型属性任务涉及的科目名称'studySubject'，（如英语、数学等广义的学科名称），int型属性'time'表示该单项学习任务持续多少分钟。除了JSON数据的输出，不要包含任何其他文本。 " +
            "输出范围:我希望你能够以详细的任务执行内容来回答我，按照每一天具体到第几天、对应的任务项（包括任务名和具体任务内容）来安排日程，要求你的回答按照时间来排序。 ";
    private static final String PLANNER_PROMPT_FIT="能力与角色:我希望你是一位熟悉健身领域的专家 " +
            "背景信息:我正在计划实现{target}，希望能够通过计划日程来培养习惯，实现这个目标 " +
            "指令:我希望你能为我规划一个{time}天的健身主题的日程计划，需要实现的目标是{target} " +
            "输出风格:我希望你能够将输出封装为一个JSON格式的数组，该数组中的每个元素代表一个任务项。每个任务项包含 表示第几天的int型属性'day',string型属性任务名'task_content',string型属性具体动作名字'name',int型属性任务组数量'task_group_num'，int型属性'num_per_group'表示一组的数量（每组多少秒或者每组多少次），string型属性任务组数量的度量单位量词'num'。除了JSON数据的输出，不要包含任何其他文本。 " +
            "输出范围:我希望你能够以详细的任务执行内容来回答我，按照每一天具体到第几天、对应的任务项（包括任务名和具体任务内容）来安排日程，要求你的回答按照时间来排序。 ";
    private String MESSAGES="{\"messages\":[{\"role\":\"user\",\"content\":\"request\"}]}";
    private String MESSAGES_STREAM="{\"messages\":[{\"role\":\"user\",\"content\":\"request\"}],\"stream\":true}";

    //无prompt，生成最普通的请求message(不推荐使用)
    public String getJsonMessage(String request){
        String res=MESSAGES.replace("request",request);
        return res;
    }
    //无prompt stream(不推荐使用)
    public String getJsonMessageStream(String request){
        String res=MESSAGES_STREAM.replace("request",request);
        return res;
    }
    //prompt型
    //普通计划类型message
    public String getJsonMessageStreamPrompt(String theme,String target,String time){
        String res=PLANNER_PROMPT;
        res=res.replaceAll("theme",theme);
        res=res.replaceAll("target",target);
        res=res.replaceAll("time",time);
        res=MESSAGES_STREAM.replace("request",res);
        return res;
    }
    public String getJsonMessageFitPrompt(String target,String time){
        String res=PLANNER_PROMPT_FIT;
        res=res.replaceAll("target",target);
        res=res.replaceAll("time",time);
        res=MESSAGES_STREAM.replace("request",res);
        return res;
    }
    public String getJsonMessageStudyPrompt(String target,String time){
        String res=PLANNER_PROMPT_STUDY;
        res=res.replaceAll("target",target);
        res=res.replaceAll("time",time);
        res=MESSAGES_STREAM.replace("request",res);
        return res;
    }
}
