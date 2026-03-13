package teccr.justdoitcloud.service.external.taskarchiver;

import teccr.justdoitcloud.data.User;
import teccr.justdoitcloud.data.Task;

/**
 * Interfaz para archivar tareas.
 */
public interface TaskArchiver {

    /**
     * Archiva la tarea provista para el usuario indicado.
     *
     * @param user usuario asociado a la tarea
     * @param task tarea a archivar
     */
    void archiveTask(User user, Task task);
}