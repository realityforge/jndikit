require 'buildr/gpg'
require 'buildr/custom_pom'
require 'buildr/jacoco'
require 'buildr/git_auto_version'

desc "JNDI Kit: a toolkit designed to help with the construction of JNDI providers"
define 'jndikit' do
  project.group = 'org.realityforge.jndikit'
  compile.options.source = '1.6'
  compile.options.target = '1.6'
  compile.options.lint = 'all'

  project.version = ENV['PRODUCT_VERSION'] if ENV['PRODUCT_VERSION']

  pom.add_apache_v2_license
  pom.add_github_project('realityforge/jndikit')
  pom.add_developer('realityforge', 'Peter Donald')
  pom.provided_dependencies.concat [:javax_naming]

  compile.with :javax_naming
  test.using :junit

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
