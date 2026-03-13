package teccr.justdoitcloud.service.external.taskarchiver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import teccr.justdoitcloud.data.Task;
import teccr.justdoitcloud.data.User;
import teccr.justdoitcloud.service.external.dto.DummyJsonTODODto;


@Slf4j
@Service
public class TaskArchiverImpl implements TaskArchiver {

    private final RestTemplate restTemplate;
    private final int externalUserId;
    private final String ENDPOINT = "https://dummyjson.com/todos/add";

    public TaskArchiverImpl(@Value("${external.taskarchiver.userId}") int externalUserId,
                                 RestTemplate restTemplate) {
        this.externalUserId = externalUserId;
        this.restTemplate = restTemplate;
    }

    @Override
    public void archiveTask(User user, Task task) {
        if (user == null || task == null) {
            return;
        }

        String todoBody =  user.getName() + " - " + task.getDescription();

        boolean completed = task.getStatus().equals(Task.Status.DONE);

        DummyJsonTODODto payloadDto = new DummyJsonTODODto();
        payloadDto.setTodo(todoBody);
        payloadDto.setCompleted(completed);
        payloadDto.setUserId(externalUserId);

        restTemplate.postForObject(ENDPOINT, payloadDto, DummyJsonTODODto.class);
        log.info("Externally archived task for user {}: {}", user.getName(), payloadDto.toString());

    }
}
