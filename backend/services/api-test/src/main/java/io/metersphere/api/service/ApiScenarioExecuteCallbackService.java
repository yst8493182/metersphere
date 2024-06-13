package io.metersphere.api.service;

import io.metersphere.api.invoker.ApiExecuteCallbackServiceInvoker;
import io.metersphere.api.service.scenario.ApiScenarioBatchRunService;
import io.metersphere.api.service.scenario.ApiScenarioRunService;
import io.metersphere.sdk.constants.ApiExecuteResourceType;
import io.metersphere.sdk.dto.api.task.GetRunScriptRequest;
import io.metersphere.sdk.dto.api.task.GetRunScriptResult;
import io.metersphere.sdk.dto.queue.ExecutionQueue;
import io.metersphere.sdk.dto.queue.ExecutionQueueDetail;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: jianxing
 * @CreateTime: 2024-02-06  20:47
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ApiScenarioExecuteCallbackService implements ApiExecuteCallbackService {
    @Resource
    private ApiScenarioRunService apiScenarioRunService;
    @Resource
    private ApiScenarioBatchRunService apiScenarioBatchRunService;

    public ApiScenarioExecuteCallbackService() {
        ApiExecuteCallbackServiceInvoker.register(ApiExecuteResourceType.API_SCENARIO, this);
    }

    /**
     * 解析并返回执行脚本
     */
    @Override
    public GetRunScriptResult getRunScript(GetRunScriptRequest request) {
        return apiScenarioRunService.getRunScript(request);
    }

    /**
     * 串行时，执行下一个任务
     * @param queue
     * @param queueDetail
     */
    @Override
    public void executeNextTask(ExecutionQueue queue, ExecutionQueueDetail queueDetail) {
        apiScenarioBatchRunService.executeNextTask(queue, queueDetail);
    }
}
