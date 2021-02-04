package top.jiangliuhong.fixjson.component;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * ResourceBundleControl <br/>
 * 国际化资源包控制器
 * 
 * @author jiangliuhong
 * @date 2021/2/4 下午11:59
 * @version 1.0.0
 */
public class ResourceBundleControl extends ResourceBundle.Control {

    private final Charset charset;

    public ResourceBundleControl(Charset charset) {
        this.charset = requireNonNull(charset);
    }

    @Override
    public ResourceBundle newBundle(final String baseName, final Locale locale, final String format,
        final ClassLoader loader, final boolean reload)
        throws IllegalAccessException, InstantiationException, IOException {

        final String bundleName = toBundleName(baseName, locale);
        final String resourceName = toResourceName(bundleName, "properties");

        ResourceBundle bundle = null;
        InputStream stream = null;
        if (reload) {
            URL url = loader.getResource(resourceName);
            if (url != null) {
                URLConnection connection = url.openConnection();
                if (connection != null) {
                    connection.setUseCaches(false);
                    stream = connection.getInputStream();
                }
            }
        } else {
            stream = loader.getResourceAsStream(resourceName);
        }

        if (stream != null) {
            try {
                bundle = new PropertyResourceBundle(new InputStreamReader(stream, charset));
            } finally {
                stream.close();
            }
        }

        return bundle;
    }
}
