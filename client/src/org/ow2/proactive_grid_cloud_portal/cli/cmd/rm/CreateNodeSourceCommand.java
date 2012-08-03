/*
 * ################################################################
 *
 * ProActive Parallel Suite(TM): The Java(TM) library for
 *    Parallel, Distributed, Multi-Core Computing for
 *    Enterprise Grids & Clouds
 *
 * Copyright (C) 1997-2012 INRIA/University of
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
 * ################################################################
 * $$ACTIVEEON_INITIAL_DEV$$
 */

package org.ow2.proactive_grid_cloud_portal.cli.cmd.rm;

import static org.ow2.proactive_grid_cloud_portal.cli.CLIException.REASON_INVALID_ARGUMENTS;
import static org.ow2.proactive_grid_cloud_portal.cli.HttpResponseStatus.OK;
import static org.ow2.proactive_grid_cloud_portal.cli.cmd.rm.SetInfrastructureCommand.SET_INFRASTRUCTURE;
import static org.ow2.proactive_grid_cloud_portal.cli.cmd.rm.SetNodeSourceCommand.SET_NODE_SOURCE;
import static org.ow2.proactive_grid_cloud_portal.cli.cmd.rm.SetPolicyCommand.SET_POLICY;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.ow2.proactive_grid_cloud_portal.cli.CLIException;
import org.ow2.proactive_grid_cloud_portal.cli.cmd.AbstractCommand;
import org.ow2.proactive_grid_cloud_portal.cli.cmd.Command;

public class CreateNodeSourceCommand extends AbstractCommand implements Command {
    private String nodeSource;

    public CreateNodeSourceCommand(String nodeSource) {
        this.nodeSource = nodeSource;
    }

    @Override
    public void execute() throws CLIException {
        String infrastructure = context().getProperty(SET_INFRASTRUCTURE,
                String.class);
        String policy = context().getProperty(SET_POLICY, String.class);
        if (infrastructure == null || policy == null) {
            throw new CLIException(REASON_INVALID_ARGUMENTS,
                    "No value is specified for one of infrastructure or policy parameters");
        }
        if (context().getProperty(SET_NODE_SOURCE, String.class) != null) {
            nodeSource = context().getProperty(SET_NODE_SOURCE, String.class);
        }
        HttpPost request = new HttpPost(resourceUrl("nodesource/create"));
        String requestContents = (new StringBuilder())
                .append("nodeSourceName=").append(nodeSource).append('&')
                .append(infrastructure).append('&').append(policy).toString();
        request.setEntity(new StringEntity(requestContents,
                ContentType.APPLICATION_FORM_URLENCODED));
        HttpResponse response = execute(request);
        if (statusCode(OK) == statusCode(response)) {
            boolean success = readValue(response, Boolean.TYPE);
            resultStack().push(success);
            if (success) {
                writeLine("Node source successfully created.");
            } else {
                writeLine("Cannot create node source: %s", nodeSource);
            }
        } else {
            handleError("An error occurred while creating node source:",
                    response);

        }
    }
}
