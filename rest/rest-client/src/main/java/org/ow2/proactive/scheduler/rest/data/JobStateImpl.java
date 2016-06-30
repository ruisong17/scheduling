/*
 *  
 * ProActive Parallel Suite(TM): The Java(TM) library for
 *    Parallel, Distributed, Multi-Core Computing for
 *    Enterprise Grids & Clouds
 *
 * Copyright (C) 1997-2015 INRIA/University of
 *                 Nice-Sophia Antipolis/ActiveEon
 * Contact: proactive@ow2.org or contact@activeeon.com
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation; version 3 of
 * the License.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307
 * USA
 *
 * If needed, contact us to obtain a release under GPL Version 2 or 3
 * or a different license than the AGPL.
 *
 *  Initial developer(s):               The ProActive Team
 *                        http://proactive.inria.fr/team_members.htm
 *  Contributor(s):
 *
 *  * $$PROACTIVE_INITIAL_DEV$$
 */
package org.ow2.proactive.scheduler.rest.data;

import static org.ow2.proactive.scheduler.rest.data.DataUtility.toJobInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.ow2.proactive.db.types.BigString;
import org.ow2.proactive.scheduler.common.job.JobInfo;
import org.ow2.proactive.scheduler.common.job.JobPriority;
import org.ow2.proactive.scheduler.common.job.JobState;
import org.ow2.proactive.scheduler.common.job.JobType;
import org.ow2.proactive.scheduler.common.task.TaskId;
import org.ow2.proactive.scheduler.common.task.TaskInfo;
import org.ow2.proactive.scheduler.common.task.TaskState;
import org.ow2.proactive_grid_cloud_portal.scheduler.dto.JobStateData;
import org.ow2.proactive_grid_cloud_portal.scheduler.dto.TaskStateData;


public class JobStateImpl extends JobState {

    private static final long serialVersionUID = 1L;

    private JobStateData jobStateData;

    JobStateImpl(JobStateData d) {
        this.jobStateData = d;
        copyGenericInformations(jobStateData);
    }

    private void copyGenericInformations(JobStateData jobStateData) {
        Map<String, String> genericInformations = new HashMap<>();
        for (Entry<String, BigString> entry : jobStateData.getGenericInformation().entrySet()) {
            genericInformations.put(entry.getKey(), entry.getValue().getValue());
        }
        super.setGenericInformations(genericInformations);
    }

    @Override
    public Map<TaskId, TaskState> getHMTasks() {
        throw new UnsupportedOperationException();
    }

    @Override
    public JobInfo getJobInfo() {
        return toJobInfo(jobStateData.getJobInfo());
    }

    @Override
    public String getOwner() {
        return jobStateData.getOwner();
    }

    @Override
    public List<TaskState> getTasks() {
        Map<String, TaskStateData> taskStateMap = jobStateData.getTasks();
        List<TaskState> taskStateList = new ArrayList<>(taskStateMap.size());
        for (TaskStateData ts : taskStateMap.values()) {
            taskStateList.add(DataUtility.taskState(ts));
        }
        return taskStateList;
    }

    @Override
    public List<TaskState> getTasksByTag(String tag) {
        Map<String, TaskStateData> taskStateMap = jobStateData.getTasks();
        List<TaskState> taskStateList = new ArrayList<>(taskStateMap.size());
        for (TaskStateData ts : taskStateMap.values()) {
            String taskTag = ts.getTag();
            if (taskTag != null && taskTag.equals(tag)) {
                taskStateList.add(DataUtility.taskState(ts));
            }
        }

        return taskStateList;
    }

    @Override
    public void update(TaskInfo taskInfo) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void update(JobInfo jobInfo) {
        throw new UnsupportedOperationException();

    }

    @Override
    public JobType getType() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getName() {
        return jobStateData.getName();
    }

    @Override
    public JobPriority getPriority() {
        return JobPriority.valueOf(jobStateData.getPriority());
    }
}
