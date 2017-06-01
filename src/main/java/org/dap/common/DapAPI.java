package org.dap.common;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Documented;
import java.lang.annotation.Target;

/**
 * Indicates that the annotated class, constructor, method or field is a public API.
 * <br>
 * A public API must not be changed (removed) by a new version of the code, unless that new
 * version is a new <b>major</b> version.
 * <br>
 * For details see <b>SEMVER</b>: <a>http://semver.org/</a>
 * 
 * <p>
 * (Note, however, that as long as the major version is 0, public API might change between versions,
 * even though the newer version is not a new major version.)
 * 
 * <p>
 * Interpretation:<br>
 * <ul>
 * <li>Annotating a class: this class will exist (with the same name, in the same package) in newer versions. However,
 * this does not mean that it will contain the same public constructors, methods and fields.</li>
 * <li>Annotating a constructor or method : they will exist with the same name and same arguments in newer versions.
 * The implementation might change, but not the interface (name and arguments).</li>
 * <li>Annotating a field: it will exist in newer version.</li>
 * </ul>
 * 
 * Note that this implies that constructors, methods or fields can be annotated if and only if the class itself is annotated.
 * <br>
 * Note also that private members (constructors, methods and fields) must not be annotated.
 * 
 * 
 *
 * <p>
 * Date: 30 May 2017
 * @author Asher Stern
 *
 */
@Documented
@Target({ TYPE, FIELD, METHOD, CONSTRUCTOR })
public @interface DapAPI
{

}
