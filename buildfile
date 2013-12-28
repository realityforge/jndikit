require 'buildr/jacoco'
require 'buildr/git_auto_version'

desc "JNDI Kit: a toolkit designed to help with the construction of JNDI providers"
define 'jndikit' do
  project.group = 'org.realityforge'
  compile.options.source = '1.6'
  compile.options.target = '1.6'
  compile.options.lint = 'all'

  project.version = ENV['PRODUCT_VERSION'] if ENV['PRODUCT_VERSION']

  compile.with :javax_naming

  compile do
    Buildr.ant("rmic") do |ant|
      ant.rmic :base => _(:target, :main, :classes),
               :classname => "org.realityforge.spice.jndikit.rmi.server.RMINamingProviderImpl",
               :stubVersion => "1.2",
               :classpath => compile.dependencies.join(";")
    end
  end

  package(:jar)
  package(:sources)
  package(:javadoc)
end
