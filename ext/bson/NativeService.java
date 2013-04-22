package org.bson;

import java.io.IOException;

import org.jruby.Ruby;
import org.jruby.RubyModule;
import org.jruby.runtime.load.BasicLibraryService;

/**
 * The native implementation of various extensions.
 *
 * @since 2.0.0
 */
public class NativeService implements BasicLibraryService {

  /**
   * Constant for the BSON module name.
   *
   * @since 2.0.0
   */
  private final String BSON = "BSON".intern();

  /**
   * Loads the native extension into the JRuby runtime.
   *
   * Example: service.basicLoad(ruby);
   *
   * @param runtime The Ruby runtime.
   *
   * @return Always returns true if no exception.
   *
   * @since 2.0.0
   */
  public boolean basicLoad(Ruby runtime) throws IOException {
    RubyModule bson = runtime.fastGetModule(BSON);
    new IntegerExtension(bson).redefine();
    return true;
  }

  /**
   * Provides native extensions around integer operations.
   *
   * @since 2.0.0
   */
  private class IntegerExtension {

    /**
     * Constant for the Integer module name.
     *
     * @since 2.0.0
     */
    private final String INTEGER = "Integer".intern();

    /**
     * The service's integer module to operate on.
     *
     * @since 2.0.0
     */
    private RubyModule integer;

    /**
     * Instantiate a new integer extender.
     *
     * @param bson The parent BSON module.
     *
     * @since 2.0.0.
     */
    private IntegerExtension(final RubyModule bson) {
      this.integer = bson.defineOrGetModuleUnder(INTEGER);
    }

    /**
     * Load the method definitions into the integer module.
     *
     * @since 2.0.0.
     */
    public void redefine() {
      integer.defineAnnotatedMethods(IntegerExtension.class);
    }
  }
}
