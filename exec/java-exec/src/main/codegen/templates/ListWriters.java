/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

<@pp.dropOutputFile />

<#list ["Single", "Repeated"] as mode>
<@pp.changeOutputFile name="/org/apache/drill/exec/vector/complex/impl/${mode}ListWriter.java" />


<#include "/@includes/license.ftl" />

package org.apache.drill.exec.vector.complex.impl;
<#if mode == "Single">
  <#assign containerClass = "AbstractContainerVector" />
  <#assign index = "idx()">
<#else>
  <#assign containerClass = "RepeatedListVector" />
  <#assign index = "currentChildIndex">
</#if>


<#include "/@includes/vv_imports.ftl" />

/* This class is generated using freemarker and the ListWriters.java template */
@SuppressWarnings("unused")
public class ${mode}ListWriter extends AbstractFieldWriter{
  
  static enum Mode { INIT, IN_MAP, IN_LIST <#list vv.types as type><#list type.minor as minor>, IN_${minor.class?upper_case}</#list></#list> }

  private final String name;
  protected final ${containerClass} container;
  private Mode mode = Mode.INIT;
  private FieldWriter writer;
  protected RepeatedValueVector innerVector;
  
  <#if mode == "Repeated">private int currentChildIndex = 0;</#if>
  public ${mode}ListWriter(String name, ${containerClass} container, FieldWriter parent){
    super(parent);
    this.name = name;
    this.container = container;
  }

  public ${mode}ListWriter(${containerClass} container, FieldWriter parent){
    super(parent);
    this.name = null;
    this.container = container;
  }

  public void allocate(){
    if(writer != null){
      writer.allocate();
    }
    
    <#if mode == "Repeated">
    container.allocateNew();
    </#if>
  }
  
  public void clear(){
    writer.clear();
  }

  public int getValueCapacity() {
    return innerVector==null ? 0:innerVector.getValueCapacity();
  }

  public void setValueCount(int count){
    if(innerVector != null) innerVector.getMutator().setValueCount(count);
  }
  
  public MapWriter map(){
    switch(mode){
    case INIT:
      int vectorCount = container.size();
      RepeatedMapVector vector = container.addOrGet(name, RepeatedMapVector.TYPE, RepeatedMapVector.class);
      innerVector = vector;
      writer = new RepeatedMapWriter(vector, this);
      if(vectorCount != container.size()) writer.allocate();
      writer.setPosition(${index});
      mode = Mode.IN_MAP;
      return writer;
    case IN_MAP:
      return writer;
    }

  throw UserException.unsupportedError().message(getUnsupportedErrorMsg("MAP", mode.name())).build();

  }
  
  public ListWriter list(){
    switch(mode){
    case INIT:
      int vectorCount = container.size();
      RepeatedListVector vector = container.addOrGet(name, RepeatedListVector.TYPE, RepeatedListVector.class);
      innerVector = vector;
      writer = new RepeatedListWriter(null, vector, this);
      if(vectorCount != container.size()) writer.allocate();
      writer.setPosition(${index});
      mode = Mode.IN_LIST;
      return writer;
    case IN_LIST:
      return writer;
    }

  throw UserException.unsupportedError().message(getUnsupportedErrorMsg("LIST", mode.name())).build();

  }
  
  <#list vv.types as type><#list type.minor as minor>
  <#assign lowerName = minor.class?uncap_first />
  <#assign upperName = minor.class?upper_case />
  <#assign capName = minor.class?cap_first />
  <#if lowerName == "int" ><#assign lowerName = "integer" /></#if>
  
  private static final MajorType ${upperName}_TYPE = Types.repeated(MinorType.${upperName});
  
  public ${capName}Writer ${lowerName}(){
    switch(mode){
    case INIT:
      int vectorCount = container.size();
      Repeated${capName}Vector vector = container.addOrGet(name, ${upperName}_TYPE, Repeated${capName}Vector.class);   
      innerVector = vector;
      writer = new Repeated${capName}WriterImpl(vector, this);
      if(vectorCount != container.size()) writer.allocate();
      writer.setPosition(${index});
      mode = Mode.IN_${upperName};
      return writer;
    case IN_${upperName}:
      return writer;
    }

  throw UserException.unsupportedError().message(getUnsupportedErrorMsg("${upperName}", mode.name())).build();

  }
  </#list></#list>

  public MaterializedField getField() {
    return container.getField();
  }

  <#if mode == "Repeated">
  
  public void start(){
    
    final RepeatedListVector list = (RepeatedListVector) container;
    final RepeatedListVector.RepeatedMutator mutator = list.getMutator();
    
    // make sure that the current vector can support the end position of this list.
    if(container.getValueCapacity() <= idx()){
      mutator.setValueCount(idx()+1);
    }
    
    // update the repeated vector to state that there is current+1 objects.
    RepeatedListHolder h = new RepeatedListHolder();
    list.getAccessor().get(idx(), h);
    if(h.start >= h.end){
      mutator.startNewValue(idx());  
    }
    currentChildIndex = container.getMutator().add(idx());
    if(writer != null){
      writer.setPosition(currentChildIndex);  
    }
  }
  
  
  
  public void end(){
    // noop, we initialize state at start rather than end.
  }
  <#else>
  
  
  public void setPosition(int index){
    super.setPosition(index);
    if(writer != null) writer.setPosition(index);
  }
  
  public void start(){
    // noop
  }
  
  public void end(){
    // noop
  }
  </#if>

  private String getUnsupportedErrorMsg(String expected, String found ){
    String f = found.substring(3);
    return String.format("In a list of type %s, encountered a value of type %s. "+
      "Drill does not support lists of different types.",
       f, expected
    );
  }

  }
</#list>


