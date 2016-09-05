package pl.wroc.uni.ii.mjanuszkiewicz.deep
package compilation

class ConfigurationCompiler(stats: StatsConfiguration) {
  val compiled: EntryProcessor = new EntryProcessor(stats.configure)
}
