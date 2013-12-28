require 'buildr/jacoco'

desc "JNDI Kit: a toolkit designed to help with the construction of JNDI providers"
define('jndikit') do
  project.version = `git describe --tags`.strip
  project.group = 'org.realityforge'
  compile.options.source = '1.6'
  compile.options.target = '1.6'
  compile.options.lint = 'all'

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
end
