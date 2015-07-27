Embulk::JavaPlugin.register_encoder(
  "xz", "org.embulk.encoder.xz.XzEncoderPlugin",
  File.expand_path('../../../../classpath', __FILE__))
