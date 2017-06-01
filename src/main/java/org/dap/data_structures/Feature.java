package org.dap.data_structures;

import java.io.Serializable;

import org.dap.common.DapAPI;


/**
 * A feature is a property of a document. A feature is <b>not</b> an annotation, it does not
 * specify a span of the document's text. Rather, it is a property of the entire document.
 * A common example is the language feature, which indicates the language of the document.
 *
 * <p>
 * Date: 30 May 2017
 * @author Asher Stern
 *
 */
@DapAPI
public interface Feature extends Serializable
{

}
