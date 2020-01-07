package com.jcrworkflow.impl;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowProcess;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import java.util.Dictionary;



import javax.jcr.Session;
import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
    
@Component(
immediate = true,
label = "Submit to JCR Workflow",
metatype = true
)
@Properties({@Property(
name = "formName",
description = "Provide the Form Name"
), @Property(
label = "Workflow Label",
name = "process.label",
value = {"Submit to JCR Workflow"},
description = "Submit to JCR Workflow"
)})

@Service

public class JCRWorkflowSubmit implements WorkflowProcess {

	private String formName;

public void execute(WorkItem workItem, WorkflowSession session, MetaDataMap metaDataMap) throws WorkflowException {
  String AFName = null;
  String payloadPath;
  if (metaDataMap.containsKey("PROCESS_ARGS")) {
     System.out.println("The Column Name is " + ((String)metaDataMap.get("PROCESS_ARGS", "string")).toString());
     payloadPath = (String)metaDataMap.get("PROCESS_ARGS", "string");
     String[] values = payloadPath.split(",");
     AFName = values[0];
     System.out.println("***The Adative Form Name is " + values[0]);
  }

  payloadPath = workItem.getWorkflowData().getPayload().toString();
  System.out.println("The Current Assignee is " + workItem.getCurrentAssignee());
  System.out.println("The Payload For InsertFormData  is " + workItem.getWorkflowData().getPayload().toString());
  Session jcrSession = (Session)session.adaptTo(Session.class);
  String dataFilePath = payloadPath + "/Data.xml/jcr:content";
  System.out.println("The Data File Path is " + dataFilePath);
}

@Activate
protected void activate(ComponentContext ctx) {
  Dictionary props = ctx.getProperties();
 this.formName = (String)props.get("formName");
System.out.println("The Form Name  is" + this.formName);
}

	/** Default log. */
  protected final Logger log = LoggerFactory.getLogger(this.getClass());
             
}

   