/**
 * This file Copyright (c) 2005-2010 Aptana, Inc. This program is
 * dual-licensed under both the Aptana Public License and the GNU General
 * Public license. You may elect to use one or the other of these licenses.
 * 
 * This program is distributed in the hope that it will be useful, but
 * AS-IS and WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE, TITLE, or
 * NONINFRINGEMENT. Redistribution, except as permitted by whichever of
 * the GPL or APL you select, is prohibited.
 *
 * 1. For the GPL license (GPL), you can redistribute and/or modify this
 * program under the terms of the GNU General Public License,
 * Version 3, as published by the Free Software Foundation.  You should
 * have received a copy of the GNU General Public License, Version 3 along
 * with this program; if not, write to the Free Software Foundation, Inc., 51
 * Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 * 
 * Aptana provides a special exception to allow redistribution of this file
 * with certain other free and open source software ("FOSS") code and certain additional terms
 * pursuant to Section 7 of the GPL. You may view the exception and these
 * terms on the web at http://www.aptana.com/legal/gpl/.
 * 
 * 2. For the Aptana Public License (APL), this program and the
 * accompanying materials are made available under the terms of the APL
 * v1.0 which accompanies this distribution, and is available at
 * http://www.aptana.com/legal/apl/.
 * 
 * You may view the GPL, Aptana's exception and additional terms, and the
 * APL in the file titled license.html at the root of the corresponding
 * plugin containing this source file.
 * 
 * Any modifications to this file must keep this entire header intact.
 */

package org.python.pydev.django_templates.html.editor;

import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.IVerticalRuler;
import org.eclipse.swt.widgets.Composite;
import org.python.pydev.django_templates.IDjConstants;
import org.python.pydev.django_templates.editor.DjEditor;
import org.python.pydev.django_templates.html.outline.DjHTMLOutlineContentProvider;
import org.python.pydev.django_templates.html.outline.DjHTMLOutlineLabelProvider;

import com.aptana.editor.common.outline.CommonOutlinePage;
import com.aptana.editor.common.parsing.FileService;
import com.aptana.editor.html.HTMLEditor;
import com.aptana.editor.html.parsing.HTMLParseState;

/**
 * @author Fabio Zadrozny
 */
public class DjHTMLEditor extends HTMLEditor {

    private DjEditor djEditor;
    
    /*
     * (non-Javadoc)
     * 
     * @see com.aptana.editor.common.AbstractThemeableEditor#initializeEditor()
     */
    @Override
    protected void initializeEditor() {
        super.initializeEditor();
        djEditor = new DjEditor();
        this.djEditor.registerPrefChangeListener(this.getISourceViewer());
        
        setSourceViewerConfiguration(new DjHTMLSourceViewerConfiguration(this.djEditor.getChainedPrefStore(), this));
        setDocumentProvider(new DjHTMLDocumentProvider());
    }
    
    @Override
    protected ISourceViewer createSourceViewer(Composite parent, IVerticalRuler ruler, int styles) {
        ISourceViewer viewer = super.createSourceViewer(parent, ruler, styles);
        djEditor.onCreateSourceViewer(viewer);
        return viewer;
    }


    @Override
    public void dispose() {
        super.dispose();
        djEditor.dispose();
    }

    @Override
    protected FileService createFileService() {
        return new FileService(IDjConstants.LANGUAGE_DJANGO_TEMPLATES_HTML, new HTMLParseState());
    }

    @Override
    protected CommonOutlinePage createOutlinePage() {
        CommonOutlinePage outline = super.createOutlinePage();
        outline.setContentProvider(new DjHTMLOutlineContentProvider());
        outline.setLabelProvider(new DjHTMLOutlineLabelProvider(getFileService().getParseState()));

        return outline;
    }

    @Override
    protected char[] getPairMatchingCharacters() {
        return this.djEditor.getPairMatchingCharacters(super.getPairMatchingCharacters());
    }
}
