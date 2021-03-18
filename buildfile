require 'buildr/gpg'
require 'buildr/jacoco'
require 'buildr/git_auto_version'

desc 'JNDI Kit: a toolkit designed to help with the construction of JNDI providers'
define 'jndikit' do
  project.group = 'org.realityforge.jndikit'
  compile.options.source = '1.8'
  compile.options.target = '1.8'
  compile.options.lint = 'all'

  project.version = ENV['PRODUCT_VERSION'] if ENV['PRODUCT_VERSION']

  pom.add_apache_v2_license
  pom.add_github_project('realityforge/jndikit')
  pom.add_developer('realityforge', 'Peter Donald')
  pom.provided_dependencies.concat [:javax_naming]

  compile.with :javax_naming

  package(:jar)
  package(:sources)
  package(:javadoc)
end
