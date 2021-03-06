//$HeadURL: svn+ssh://lbuesching@svn.wald.intevation.de/deegree/base/trunk/resources/eclipse/files_template.xml $
/*----------------------------------------------------------------------------
 This file is part of deegree, http://deegree.org/
 Copyright (C) 2001-2010 by:
 - Department of Geography, University of Bonn -
 and
 - lat/lon GmbH -

 This library is free software; you can redistribute it and/or modify it under
 the terms of the GNU Lesser General Public License as published by the Free
 Software Foundation; either version 2.1 of the License, or (at your option)
 any later version.
 This library is distributed in the hope that it will be useful, but WITHOUT
 ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 details.
 You should have received a copy of the GNU Lesser General Public License
 along with this library; if not, write to the Free Software Foundation, Inc.,
 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA

 Contact information:

 lat/lon GmbH
 Aennchenstr. 19, 53177 Bonn
 Germany
 http://lat-lon.de/

 Department of Geography, University of Bonn
 Prof. Dr. Klaus Greve
 Postfach 1147, 53001 Bonn
 Germany
 http://www.geographie.uni-bonn.de/deegree/

 e-mail: info@deegree.org
 ----------------------------------------------------------------------------*/
package org.deegree.client.mdeditor.gui.creation;

import static org.deegree.client.mdeditor.gui.GuiUtils.*;
import static org.deegree.client.mdeditor.model.LAYOUT_TYPE.*;

import java.util.ArrayList;
import java.util.List;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UIOutput;
import javax.faces.component.UIParameter;
import javax.faces.component.UISelectItem;
import javax.faces.component.behavior.AjaxBehavior;
import javax.faces.component.html.HtmlColumn;
import javax.faces.component.html.HtmlCommandButton;
import javax.faces.component.html.HtmlCommandLink;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.component.html.HtmlGraphicImage;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlInputTextarea;
import javax.faces.component.html.HtmlOutcomeTargetLink;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlPanelGrid;
import javax.faces.component.html.HtmlPanelGroup;
import javax.faces.component.html.HtmlSelectManyListbox;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.context.FacesContext;
import javax.faces.event.PreRenderComponentEvent;

import org.deegree.client.mdeditor.configuration.ConfigurationException;
import org.deegree.client.mdeditor.gui.GuiUtils;
import org.deegree.client.mdeditor.gui.ReferencedElementBean;
import org.deegree.client.mdeditor.gui.GuiUtils.ACTION_ATT_VALUES;
import org.deegree.client.mdeditor.gui.components.HtmlInputManyText;
import org.deegree.client.mdeditor.gui.components.HtmlInputTextItems;
import org.deegree.client.mdeditor.gui.components.ListGroup;
import org.deegree.client.mdeditor.gui.listener.CodeListCreator;
import org.deegree.client.mdeditor.gui.listener.DataGroupListener;
import org.deegree.client.mdeditor.gui.listener.DataGroupSelectListener;
import org.deegree.client.mdeditor.gui.listener.FormFieldValueChangedListener;
import org.deegree.client.mdeditor.gui.listener.HelpClickedListener;
import org.deegree.client.mdeditor.gui.listener.GroupReferenceListCreator;
import org.deegree.client.mdeditor.model.FormConfiguration;
import org.deegree.client.mdeditor.model.FormElement;
import org.deegree.client.mdeditor.model.FormField;
import org.deegree.client.mdeditor.model.FormGroup;
import org.deegree.client.mdeditor.model.INPUT_TYPE;
import org.deegree.client.mdeditor.model.InputFormField;
import org.deegree.client.mdeditor.model.LAYOUT_TYPE;
import org.deegree.client.mdeditor.model.ReferencedElement;
import org.deegree.client.mdeditor.model.SELECT_TYPE;
import org.deegree.client.mdeditor.model.SelectFormField;

/**
 * TODO add class documentation here
 * 
 * @author <a href="mailto:buesching@lat-lon.de">Lyn Buesching</a>
 * @author last edited by: $Author: lyn $
 * 
 * @version $Revision: $, $Date: $
 */
public class FormCreator {

    private static final String MD_RB = "mdLabels";

    private static final String CONF_RB = "confLabels";

    public static HtmlPanelGrid createForm( String confId, String grpId, FormConfiguration formConf, boolean global )
                            throws ConfigurationException {
        FormGroup fg = null;
        for ( FormGroup formGroup : formConf.getFormGroups() ) {
            if ( formGroup.getId().equals( grpId ) ) {
                fg = formGroup;
            }
        }
        if ( fg != null ) {
            HtmlPanelGrid grid = new HtmlPanelGrid();
            grid.setId( getUniqueId() );
            FacesContext fc = FacesContext.getCurrentInstance();
            addMenu( fc, confId, formConf, grid );
            addFormGroup( fc, confId, grid, fg, true, global );
            grid.getAttributes().put( GROUPID_ATT_KEY, fg.getId() );
            return grid;
        }
        // ?
        return null;
    }

    private static void addMenu( FacesContext fc, String confId, FormConfiguration formConf, HtmlPanelGrid grid ) {

        LAYOUT_TYPE layoutType = formConf.getLayoutType();
        String menuId = null;
        String listId = null;
        if ( MENU.equals( layoutType ) ) {
            menuId = "verticalMenu";
            listId = "verticalList";
        } else if ( TAB.equals( layoutType ) ) {
            menuId = "horizontalMenu";
            listId = "horizontalList";
        }

        ListGroup listGroup = new ListGroup();
        listGroup.setId( GuiUtils.getUniqueId() );
        listGroup.getAttributes().put( "listId", listId );
        listGroup.getAttributes().put( "menuId", menuId );
        for ( FormGroup formGroup : formConf.getFormGroups() ) {
            listGroup.getChildren().add( createLink( fc, formGroup ) );
        }
        grid.getChildren().add( listGroup );
    }

    private static HtmlOutcomeTargetLink createLink( FacesContext fc, FormGroup formGroup ) {

        HtmlOutcomeTargetLink link = new HtmlOutcomeTargetLink();
        link.setId( GuiUtils.getUniqueId() );
        addTextValueVE( fc, link, CONF_RB, formGroup.getLabel() );

        link.setOutcome( "emptyForm" );
        UIParameter param = new UIParameter();
        param.setId( GuiUtils.getUniqueId() );
        param.setName( "grpId" );
        param.setValue( formGroup.getId() );
        link.getChildren().add( param );

        UIParameter param2 = new UIParameter();
        param2.setId( GuiUtils.getUniqueId() );
        param2.setName( "mdForm:emptyForm" );
        param2.setValue( "submit" );
        link.getChildren().add( param2 );

        String el = "#{editorBean.grpId == '" + formGroup.getId() + "' ? 'menuItemActive' : 'menuItemInactive'}";
        ValueExpression ve = fc.getApplication().getExpressionFactory().createValueExpression( fc.getELContext(), el,
                                                                                               String.class );
        link.setValueExpression( "styleClass", ve );
        return link;
    }

    private static void addTextValueVE( FacesContext fc, UIComponent component, String resourceBundle, String key ) {
        String el = "#{" + resourceBundle + "['" + key + "']}";
        ValueExpression ve = fc.getApplication().getExpressionFactory().createValueExpression( fc.getELContext(), el,
                                                                                               String.class );
        component.setValueExpression( "value", ve );
    }

    private static void addFormGroup( FacesContext fc, String confId, HtmlPanelGrid parentGrid, FormGroup fg,
                                      boolean isMain, boolean isGlobal )
                            throws ConfigurationException {
        HtmlPanelGrid grid = new HtmlPanelGrid();
        grid.setId( getUniqueId() );
        grid.setColumns( 3 );
        grid.setHeaderClass( "mdFormHeader" + ( isMain ? "" : " mdFormHeaderSub" ) );
        grid.setStyleClass( "mdForm" + ( isMain ? "" : " mdFormSub" ) );

        // label
        UIOutput title = new UIOutput();
        addTextValueVE( fc, title, CONF_RB, fg.getTitle() );
        grid.getFacets().put( "header", title );

        // createInputPanelGroup();
        for ( FormElement fe : fg.getFormElements() ) {
            if ( fe instanceof FormGroup ) {
                grid.getChildren().add( new HtmlPanelGroup() );
                addFormGroup( fc, confId, grid, (FormGroup) fe, false, false );
                grid.getChildren().add( new HtmlPanelGroup() );
            } else {
                addFormField( fc, confId, grid, (FormField) fe );
            }
        }

        parentGrid.getChildren().add( grid );

        if ( isGlobal || fg.getOccurence() != 1 ) {
            HtmlPanelGrid referencedGrid = new HtmlPanelGrid();
            addReferencedFormGroup( fc, fg, referencedGrid, isGlobal );
            parentGrid.getChildren().add( referencedGrid );
        }
    }

    private static void addReferencedFormGroup( FacesContext fc, FormGroup fg, HtmlPanelGrid grid, boolean isGlobal ) {
        Application app = fc.getApplication();
        ExpressionFactory ef = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();

        addButtons( fc, fg.getId(), grid, isGlobal );

        // list
        HtmlDataTable dataTable = new HtmlDataTable();
        dataTable.setId( getUniqueId() );
        dataTable.setStyleClass( "dgList" );
        dataTable.setHeaderClass( "dgListHeader" );
        dataTable.setRowClasses( "dgListRowOdd, dgListRowEven" );
        dataTable.setVar( "fgi" );

        String elRendered = "#{!empty dataGroupBean.dataGroups['" + fg.getId() + "']}";
        ValueExpression veRendered = ef.createValueExpression( elContext, elRendered, Boolean.class );
        dataTable.setValueExpression( "rendered", veRendered );

        String el = "#{dataGroupBean.dataGroups['" + fg.getId() + "']}";
        ValueExpression ve = ef.createValueExpression( elContext, el, Object.class );
        dataTable.setValueExpression( "value", ve );

        HtmlColumn colNo = new HtmlColumn();
        HtmlOutputText number = new HtmlOutputText();
        // String elNo ="";
        // ValueExpression veNo = ef.createValueExpression( elContext, elNo, Integer.class );
        // number.setValueExpression( "value", veNo );
        colNo.getChildren().add( number );
        HtmlOutputText headerNo = new HtmlOutputText();
        addTextValueVE( fc, headerNo, MD_RB, "formGroup_no" );
        colNo.getFacets().put( "header", headerNo );
        dataTable.getChildren().add( colNo );

        for ( FormElement fe : fg.getFormElements() ) {
            if ( fe instanceof FormField ) {
                HtmlColumn col = new HtmlColumn();
                col.setId( getUniqueId() );
                HtmlOutputText value = new HtmlOutputText();

                String elValue = "#{fgi.values['" + ( (FormField) fe ).getPath() + "']}";
                ValueExpression veValue = ef.createValueExpression( elContext, elValue, Object.class );
                value.setValueExpression( "value", veValue );

                HtmlOutputText header = new HtmlOutputText();
                addTextValueVE( fc, header, CONF_RB, ( (FormField) fe ).getLabel() );
                col.getChildren().add( value );
                col.getFacets().put( "header", header );
                dataTable.getChildren().add( col );
            }
        }

        HtmlColumn colOptions = new HtmlColumn();
        HtmlCommandButton selectButton = createSelectButton( fc, fg, isGlobal );
        colOptions.getChildren().add( selectButton );
        HtmlOutputText headerOptions = new HtmlOutputText();
        addTextValueVE( fc, headerOptions, MD_RB, "formGroup_options" );
        colOptions.getFacets().put( "header", headerOptions );

        StringBuilder styleClassSB = new StringBuilder();
        styleClassSB.append( "dgListNoColumn " );
        for ( int i = 0; i < dataTable.getChildCount(); i++ ) {
            styleClassSB.append( "dgListColumn, " );
        }
        styleClassSB.append( "dgListOptionColumn " );

        dataTable.setColumnClasses( styleClassSB.toString() );
        dataTable.getChildren().add( colOptions );

        grid.getChildren().add( new HtmlPanelGroup() );
        grid.getChildren().add( dataTable );
        grid.getChildren().add( new HtmlPanelGroup() );

    }

    private static void addButtons( FacesContext fc, String grpId, HtmlPanelGrid grid, boolean isReferencedGrp ) {
        HtmlPanelGrid btGrid = new HtmlPanelGrid();
        btGrid.setColumns( 5 );
        HtmlCommandButton saveButton = createButton( fc, grpId, "saveFormGroup", ACTION_ATT_VALUES.SAVE, true,
                                                     isReferencedGrp );
        HtmlCommandButton newButton = createButton( fc, grpId, "newFormGroup", ACTION_ATT_VALUES.NEW, false,
                                                    isReferencedGrp );
        HtmlCommandButton editButton = createButton( fc, grpId, "editFormGroup", ACTION_ATT_VALUES.EDIT, false,
                                                     isReferencedGrp );
        HtmlCommandButton resetButton = createButton( fc, grpId, "resetFormGroup", ACTION_ATT_VALUES.RESET, false,
                                                      isReferencedGrp );
        HtmlCommandButton deleteButton = createButton( fc, grpId, "deleteFormGroup", ACTION_ATT_VALUES.DELETE, false,
                                                       isReferencedGrp );

        String confirmMsg = getResourceText( fc, MD_RB, "deleteFormGroup_confirmMsg" );
        deleteButton.setOnclick( "return fgListConfirmDelete('" + confirmMsg + "')" );

        btGrid.getChildren().add( saveButton );
        btGrid.getChildren().add( newButton );
        btGrid.getChildren().add( editButton );
        btGrid.getChildren().add( resetButton );
        btGrid.getChildren().add( deleteButton );
        grid.getChildren().add( btGrid );
    }

    private static HtmlCommandButton createButton( FacesContext fc, String grpId, String labelKey,
                                                   ACTION_ATT_VALUES action, boolean ifNull, boolean isReferencedGrp ) {

        Application app = fc.getApplication();
        ExpressionFactory ef = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();

        HtmlCommandButton bt = new HtmlCommandButton();
        bt.setId( getUniqueId() );
        addTextValueVE( fc, bt, MD_RB, labelKey );
        bt.getAttributes().put( GROUPID_ATT_KEY, grpId );
        bt.getAttributes().put( ACTION_ATT_KEY, action );

        UIParameter param = new UIParameter();
        param.setName( DG_ID_PARAM );
        String el = "#{dataGroupBean.selectedDataGroups['" + grpId + "']}";
        ValueExpression ve = ef.createValueExpression( elContext, el, String.class );
        param.setValueExpression( "value", ve );

        AjaxBehavior ajaxB = new AjaxBehavior();
        List<String> render = new ArrayList<String>();
        render.add( "@form" );
        ajaxB.setRender( render );
        ajaxB.addAjaxBehaviorListener( new DataGroupListener() );
        bt.addClientBehavior( bt.getDefaultEventName(), ajaxB );

        // visibility
        String elVisibility = "#{dataGroupBean.selectedDataGroups['" + grpId + "'] " + ( ifNull ? "==" : "!=" )
                              + " null}";
        ValueExpression veVisibility = ef.createValueExpression( elContext, elVisibility, Boolean.class );
        bt.setValueExpression( "rendered", veVisibility );

        bt.getChildren().add( param );

        UIParameter refParam = new UIParameter();
        refParam.setName( IS_GLOBAL_PARAM );
        refParam.setValue( isReferencedGrp );
        bt.getChildren().add( refParam );

        return bt;
    }

    private static HtmlCommandButton createSelectButton( FacesContext fc, FormGroup formGroup, boolean isGlobal ) {
        ExpressionFactory ef = fc.getApplication().getExpressionFactory();
        ELContext elContext = fc.getELContext();

        // button
        HtmlCommandButton bt = new HtmlCommandButton();
        String elBtImg = "/resources/deegree/images/pencil.png";
        ValueExpression veBtImg = ef.createValueExpression( elContext, elBtImg, String.class );
        bt.setValueExpression( "image", veBtImg );
        bt.getAttributes().put( GROUPID_ATT_KEY, formGroup.getId() );

        // file name param
        UIParameter idParam = new UIParameter();
        idParam.setName( DG_ID_PARAM );
        String elFileName = "#{fgi.id}";
        ValueExpression veFileName = ef.createValueExpression( elContext, elFileName, String.class );
        idParam.setValueExpression( "value", veFileName );
        bt.getChildren().add( idParam );

        // is referenced param
        UIParameter refParam = new UIParameter();
        refParam.setName( IS_GLOBAL_PARAM );
        refParam.setValue( isGlobal );
        bt.getChildren().add( refParam );

        AjaxBehavior ajaxSelectBt = new AjaxBehavior();
        List<String> render = new ArrayList<String>();
        render.add( "@form" );
        ajaxSelectBt.setRender( render );
        ajaxSelectBt.addAjaxBehaviorListener( new DataGroupSelectListener() );
        bt.addClientBehavior( bt.getDefaultEventName(), ajaxSelectBt );
        return bt;
    }

    private static void addFormField( FacesContext fc, String confId, HtmlPanelGrid parentGrid, FormField fe )
                            throws ConfigurationException {

        Application app = fc.getApplication();
        ExpressionFactory ef = app.getExpressionFactory();
        ELContext elContext = fc.getELContext();

        // label
        UIOutput newOutput = new UIOutput();
        addTextValueVE( fc, newOutput, CONF_RB, fe.getLabel() );
        setVisibility( fe.getPath().toString(), newOutput, ef, elContext );
        newOutput.setId( getUniqueId() );
        newOutput.setValueExpression( "styleClass", ef.createValueExpression( elContext, "mdFormLabel", String.class ) );

        parentGrid.getChildren().add( newOutput );

        addValueField( fc, confId, fe, parentGrid, ef, elContext );

        // help
        HtmlCommandLink helpLink = new HtmlCommandLink();
        helpLink.setId( getUniqueId() );
        helpLink.setStyleClass( "helpLink" );
        HtmlGraphicImage img = new HtmlGraphicImage();

        img.setTitle( getResourceText( fc, MD_RB, "helpTooltip" ) );
        img.setValueExpression( "library", ef.createValueExpression( elContext, "deegree/images", String.class ) );
        img.setValueExpression( "name", ef.createValueExpression( elContext, "help.gif", String.class ) );
        helpLink.getChildren().add( img );

        if ( fe.getHelp() != null ) {
            UIParameter param = new UIParameter();
            param.setId( getUniqueId() );
            param.setName( "mdHelp" );
            addTextValueVE( fc, param, CONF_RB, fe.getHelp() );
            helpLink.getChildren().add( param );
        }

        helpLink.getChildren().add( helpLink );

        AjaxBehavior ajaxHelp = new AjaxBehavior();
        List<String> renderHelp = new ArrayList<String>();
        renderHelp.add( ":helpOutput" );
        ajaxHelp.setRender( renderHelp );
        ajaxHelp.addAjaxBehaviorListener( new HelpClickedListener() );
        helpLink.addClientBehavior( helpLink.getDefaultEventName(), ajaxHelp );

        setVisibility( fe.getPath().toString(), helpLink, ef, elContext );

        parentGrid.getChildren().add( helpLink );
    }

    private static void addValueField( FacesContext fc, String confId, FormField fe, HtmlPanelGrid parentGrid,
                                       ExpressionFactory ef, ELContext elContext )
                            throws ConfigurationException {
        String id = fe.getId();
        UIInput input = null;
        String eventName = null;
        if ( fe instanceof InputFormField ) {
            InputFormField inputFF = (InputFormField) fe;
            if ( INPUT_TYPE.TEXTAREA.equals( inputFF.getInputType() ) ) {
                input = new HtmlInputTextarea();
            } else if ( inputFF.getOccurence() != 1 ) {
                input = new HtmlInputManyText();
                ( (HtmlInputManyText) input ).setMaxOccurence( inputFF.getOccurence() );
            } else {
                input = new HtmlInputText();
            }
            eventName = "keyup";
        } else if ( fe instanceof SelectFormField ) {
            SelectFormField se = (SelectFormField) fe;
            if ( SELECT_TYPE.MANY.equals( se.getSelectType() ) ) {
                input = new HtmlSelectManyListbox();
                if ( se.getReferenceToCodeList() != null ) {
                    input.getAttributes().put( CODE_ATT_KEY, se.getReferenceToCodeList() );
                    input.subscribeToEvent( PreRenderComponentEvent.class, new CodeListCreator() );
                } else if ( se.getReferenceToGroup() != null ) {
                    input.getAttributes().put( GROUPREF_ATT_KEY, se.getReferenceToGroup() );
                    input.getAttributes().put( CONF_ATT_KEY, confId );
                    input.subscribeToEvent( PreRenderComponentEvent.class, new GroupReferenceListCreator() );
                }
            } else {
                input = new HtmlSelectOneMenu();
                if ( se.getReferenceToCodeList() != null ) {
                    input.getAttributes().put( CODE_ATT_KEY, se.getReferenceToCodeList() );
                    input.subscribeToEvent( PreRenderComponentEvent.class, new CodeListCreator() );
                } else if ( se.getReferenceToGroup() != null ) {
                    input.getAttributes().put( GROUPREF_ATT_KEY, se.getReferenceToGroup() );
                    input.getAttributes().put( CONF_ATT_KEY, confId );
                    input.subscribeToEvent( PreRenderComponentEvent.class, new GroupReferenceListCreator() );
                }
            }
        } else if ( fe instanceof ReferencedElement ) {
            ReferencedElement re = (ReferencedElement) fe;
            ReferencedElementBean refElemBean = (ReferencedElementBean) fc.getApplication().getELResolver().getValue(
                                                                                                                      fc.getELContext(),
                                                                                                                      null,
                                                                                                                      re.getBeanName() );
            if ( refElemBean != null && refElemBean.getComponent( re ) != null ) {
                UIComponent component = refElemBean.getComponent( re );
                setVisibility( fe.getPath().toString(), component, ef, elContext );
                parentGrid.getChildren().add( component );
            }
        }
        if ( input != null ) {
            input.setId( id );
            input.getAttributes().put( FIELDPATH_ATT_KEY, fe.getPath() );
            setStyleClass( fe.getPath().toString(), input, ef, elContext );
            if ( input instanceof HtmlInputManyText ) {
                // add items
                HtmlInputTextItems items = new HtmlInputTextItems();
                items.setId( getUniqueId() );
                setValue( fe.getPath().toString(), items, ef, elContext );
                input.getChildren().add( items );
            } else {
                setValue( fe.getPath().toString(), input, ef, elContext );
            }
            setFormFieldChangedAjaxBehavior( input, eventName );
            setVisibility( fe.getPath().toString(), input, ef, elContext );
            setTitle( fe.getPath().toString(), input, ef, elContext );
            parentGrid.getChildren().add( input );
        }
    }

    /**
     * Appends a the listener {@link FormFieldValueChangedListener#} for each eventName as ajax behaviour to the
     * component. Rendered attribute is set to ""@none" and the event will execute "@this" element.
     * 
     * @param component
     *            the component to add the ajax beahviour
     * @param eventNames
     *            the names of the events to add
     */
    public static void setFormFieldChangedAjaxBehavior( UIInput component, String eventName ) {
        AjaxBehavior ajaxInput = new AjaxBehavior();
        List<String> executes = new ArrayList<String>();
        executes.add( "@this" );
        ajaxInput.setExecute( executes );
        List<String> render = new ArrayList<String>();
        render.add( "@none" );
        ajaxInput.setRender( render );
        ajaxInput.addAjaxBehaviorListener( new FormFieldValueChangedListener() );
        if ( eventName == null ) {
            eventName = component.getDefaultEventName();
        }
        component.addClientBehavior( eventName, ajaxInput );
    }

    /**
     * @param path
     *            the path identifiying the form field
     * @param component
     *            the component to add the rendered attribute
     * @param ef
     *            the ExpressionFactory
     * @param elContext
     *            the ELContext
     */
    public static void setVisibility( String path, UIComponent component, ExpressionFactory ef, ELContext elContext ) {
        String el = "#{editorBean.formFields['" + path + "'].visibility}";
        ValueExpression ve = ef.createValueExpression( elContext, el, Boolean.class );
        component.setValueExpression( "rendered", ve );
    }

    /**
     * @param path
     *            the path identifiying the form field
     * @param component
     *            the component to add the value attribute
     * @param ef
     *            the ExpressionFactory
     * @param elContext
     *            the ELContext
     */
    public static void setValue( String path, UIComponent component, ExpressionFactory ef, ELContext elContext ) {
        String el = "#{editorBean.formFields['" + path + "'].value}";
        ValueExpression ve = ef.createValueExpression( elContext, el, Object.class );
        component.setValueExpression( "value", ve );
    }

    /**
     * @param path
     *            the path identifiying the form field
     * @param component
     *            the component to add the title attribute
     * @param ef
     *            the ExpressionFactory
     * @param elContext
     *            the ELContext
     */
    public static void setTitle( String path, UIComponent component, ExpressionFactory ef, ELContext elContext ) {
        String el = "#{editorBean.formFields['" + path + "'].title}";
        ValueExpression ve = ef.createValueExpression( elContext, el, String.class );
        component.setValueExpression( "title", ve );
    }

    /**
     * @param path
     *            the path identifiying the form field
     * @param input
     *            the input component to add the styleClass attribute
     * @param ef
     *            the ExpressionFactory
     * @param elContext
     *            the ELContext
     */
    public static void setStyleClass( String path, UIInput input, ExpressionFactory ef, ELContext elContext ) {
        String el = "#{editorBean.formFields['" + path + "'].valid ? '' : 'invalidFF'} mdFormInput";
        ValueExpression ve = ef.createValueExpression( elContext, el, String.class );
        input.setValueExpression( "styleClass", ve );
    }

    public static UISelectItem getNoEntrySelectItem( FacesContext fc ) {
        UISelectItem noSelection = new UISelectItem();
        noSelection.setId( GuiUtils.getUniqueId() );

        String el = "#{mdLabels['form_noEntry']}";
        ValueExpression ve = fc.getApplication().getExpressionFactory().createValueExpression( fc.getELContext(), el,
                                                                                               String.class );
        noSelection.setValueExpression( "itemLabel", ve );
        noSelection.setItemValue( null );
        return noSelection;
    }
}
