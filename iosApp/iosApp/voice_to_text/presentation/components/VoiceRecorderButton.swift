//
//  VoiceRecorderButton.swift
//  iosApp
//
//  Created by Guido David Salem on 24/01/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared


struct VoiceRecorderButton: View {
    var displayState: DisplayState
    var onClick: () -> Void
    
    
    var body: some View {
        Button(action: onClick){
            ZStack {
                Circle()
                    .foregroundColor(.primaryColor)
                    .padding()
                icon
                    .foregroundColor(.onPrimaryColor)
            }
        }
        .frame(maxWidth: 100.0,maxHeight: 100.0)
        .accessibilityIdentifier("Voice recorder button")
    }
    
    var icon: some View {
        switch displayState{
        case .speaking:
            return Image(systemName: "stop.fill")
        case .displayingResults:
            return Image(systemName: "checkmark")
        default:
            return Image(uiImage: UIImage(named: "mic")!)
        }
    }
}

struct VoiceRecorderButton_Previews: PreviewProvider {
    static var previews: some View {
        VoiceRecorderButton(displayState: .speaking, onClick: {})
    }
}
