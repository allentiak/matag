import React, {Component} from 'react'

export default class Phase extends Component {

  static getPhases() {
    return ['UT', 'UP', 'DR', 'M1', 'BC', 'DA', 'DB', 'AB', 'FS', 'CD', 'EC', 'M2', 'ET', 'CL']
  }

  static isMainPhase(phase) {
    return phase === 'M1' || phase === 'M2'
  }

  render() {
    let classes = this.props.status
    if (this.props.active) {
      classes += ' active'

      if (this.props.activeForPlayer) {
        classes += ' active-for-player'
      } else {
        classes += ' active-for-opponent'
      }
    }

    return <span key={this.props.name} className={classes}>{this.props.name}</span>
  }
}