package org.embulk.encoder.xz;

import java.io.OutputStream;
import java.io.IOException;
import com.google.common.base.Optional;
import org.embulk.config.Config;
import org.embulk.config.ConfigDefault;
import org.embulk.config.ConfigInject;
import org.embulk.config.ConfigSource;
import org.embulk.config.Task;
import org.embulk.config.TaskSource;
import org.embulk.spi.EncoderPlugin;
import org.embulk.spi.FileOutput;
import org.embulk.spi.BufferAllocator;
import org.embulk.spi.util.FileOutputOutputStream;
import org.embulk.spi.util.OutputStreamFileOutput;
import org.tukaani.xz.LZMA2Options;
import org.tukaani.xz.XZOutputStream;

public class XzEncoderPlugin
        implements EncoderPlugin
{
    public interface PluginTask
            extends Task
    {
        @ConfigInject
        public BufferAllocator getBufferAllocator();
    }

    @Override
    public void transaction(ConfigSource config, EncoderPlugin.Control control)
    {
        PluginTask task = config.loadConfig(PluginTask.class);

        control.run(task.dump());
    }

    @Override
    public FileOutput open(TaskSource taskSource, final FileOutput fileOutput)
    {
        final PluginTask task = taskSource.loadTask(PluginTask.class);

        final FileOutputOutputStream output = new FileOutputOutputStream(fileOutput,
            task.getBufferAllocator(), FileOutputOutputStream.CloseMode.FLUSH);

        return new OutputStreamFileOutput(new OutputStreamFileOutput.Provider() {
            public OutputStream openNext() throws IOException
            {
                output.nextFile();
                return newEncoderOutputStream(task, output);
            }

            public void finish() throws IOException
            {
                output.finish();
            }

            public void close() throws IOException
            {
                output.close();
            }
        });
    }

    private static OutputStream newEncoderOutputStream(PluginTask task, OutputStream file) throws IOException
    {
        return new XZOutputStream(file, new LZMA2Options());
    }
}
