//
//  GradientSurface.swift
//  iosApp
//
//  Created by Guido David Salem on 21/01/2023.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI
import shared

struct GradientSurface: ViewModifier{
    
    @Environment(\.colorScheme) var colorSheme
    
    func body(content: Content) -> some View {
        if colorSheme == .dark{
            let gradientStart = Color(hex: 0xFF23262E)
            let gradientEnd = Color(hex: 0xFF212329)
            
            content
                .background(
                    LinearGradient(
                        gradient: Gradient(colors: [gradientStart,gradientEnd]),
                        startPoint: .top,
                        endPoint: .bottom
                    )
                )
        }
        else{
            content
                .background(Color.surfaceColor)
        }
    }
    
}

extension View {
    
    func gradientSurface() -> some View {
        modifier(GradientSurface())
    }
    
}


