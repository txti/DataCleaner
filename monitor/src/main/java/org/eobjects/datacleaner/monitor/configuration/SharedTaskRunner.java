/**
 * eobjects.org DataCleaner
 * Copyright (C) 2010 eobjects.org
 *
 * This copyrighted material is made available to anyone wishing to use, modify,
 * copy, or redistribute it subject to the terms and conditions of the GNU
 * Lesser General Public License, as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this distribution; if not, write to:
 * Free Software Foundation, Inc.
 * 51 Franklin Street, Fifth Floor
 * Boston, MA  02110-1301  USA
 */
package org.eobjects.datacleaner.monitor.configuration;

import org.eobjects.analyzer.job.concurrent.TaskListener;
import org.eobjects.analyzer.job.concurrent.TaskRunnable;
import org.eobjects.analyzer.job.concurrent.TaskRunner;
import org.eobjects.analyzer.job.tasks.Task;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

/**
 * A task runner which delegates to a shared task runner for all tenants.
 */
public class SharedTaskRunner implements TaskRunner {

    private TaskRunner _delegate;
    
    public TaskRunner getDelegate() {
        if (_delegate == null) {
            WebApplicationContext applicationContext = ContextLoader.getCurrentWebApplicationContext();
            _delegate = applicationContext.getBean(TaskRunner.class);
        }
        return _delegate;
    }
    
    public void setDelegate(TaskRunner delegate) {
        _delegate = delegate;
    }

    @Override
    public void assistExecution() {
        getDelegate().assistExecution();
    }

    @Override
    public void run(TaskRunnable taskRunnable) {
        getDelegate().run(taskRunnable);
    }

    @Override
    public void run(Task task, TaskListener listener) {
        getDelegate().run(task, listener);
    }

    @Override
    public void shutdown() {
        getDelegate().shutdown();
    }
}
