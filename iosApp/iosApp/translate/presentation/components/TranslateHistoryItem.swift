//
//  TranslateHistoryItem.swift
//  iosApp
//
//  Created by Guido David Salem on 22/01/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct TranslateHistoryItem: View {
    var item: UiHistoryItem
    var onClick: () -> Void
    
    
    var body: some View {
        Button(action: onClick){
            VStack(alignment: .leading){
                HStack{
                    SmallLanguageIcon(language: item.fromLanguage)
                        .padding(.trailing)
                    Text(item.fromText)
                        .foregroundColor(.lightBlue)
                        .font(.body)
                }
                
                .frame(maxWidth: .infinity,alignment: .leading)
                .padding(.bottom)
                
                HStack{
                    SmallLanguageIcon(language: item.toLanguage)
                        .padding(.trailing)
                    Text(item.toText)
                        .foregroundColor(.onSurfaceColor)
                        .font(.body.weight(.semibold))
                }
                
                .frame(maxWidth: .infinity,alignment: .leading)
            }
            .frame(maxWidth: .infinity)
            .padding()
            .gradientSurface()
            .cornerRadius(15)
            .shadow(radius: 5)
        }
    }
}

struct TranslateHistoryItem_Previews: PreviewProvider {
    static var previews: some View {
        TranslateHistoryItem(
            item: UiHistoryItem(
                id: 1,
                fromText: "From text to Translate",
                toText: "Texto traducido",
                fromLanguage: UiLanguage(
                    language: .english,
                    imageName: "english"),
                toLanguage: UiLanguage(
                    language: .spanish,
                    imageName: "spanish")
            ),
            onClick: {}
        )
    }
}
