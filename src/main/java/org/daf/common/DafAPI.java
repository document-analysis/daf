package org.daf.common;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Documented;
import java.lang.annotation.Target;

/**
 * Indicates that the annotated class, constructor, method or public-field is a public API.
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
 *
 * <p>
 * Date: 30 May 2017
 * @author Asher Stern
 *
 */
@Documented
@Target({ TYPE, FIELD, METHOD, CONSTRUCTOR })
public @interface DafAPI
{

}
