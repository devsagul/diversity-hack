/* ************************************************************************** */
/*                                                                            */
/*                                                        :::      ::::::::   */
/*   diff_bounding_boxes.hpp                            :+:      :+:    :+:   */
/*                                                    +:+ +:+         +:+     */
/*   By: ShnurD6 <shnurd6@mail.ru>                  +#+  +:+       +#+        */
/*                                                +#+#+#+#+#+   +#+           */
/*   Created: 2019/09/07 17:34:05 by ShnurD6           #+#    #+#             */
/*   Updated: 2019/09/08 12:34:32 by ShnurD6          ###   ########.fr       */
/*                                                                            */
/* ************************************************************************** */

#ifndef DIFF_BOUNDING_BOXES_HPP
# define DIFF_BOUNDING_BOXES_HPP

#define NPY_NO_DEPRECATED_API NPY_1_7_API_VERSION

#include <python3.6/Python.h>
#include <numpy/numpyconfig.h>
#include <numpy/arrayobject.h>
#include <vector>
#include <queue>
#include <utility>
#include <iostream>
#include <algorithm>

#include "ndarray.h"

typedef npy_uint8 data_type;

#define d_x 0
#define d_y 1
#define d_x_len 2
#define d_y_len 3

inline void	ft_assert(bool aExpr, std::string aMessg)
{
	if (!aExpr)
	{
		std::cout << "\e[31m" << aMessg << "\e[0m" << std::endl;
		std::abort();
	}
}

#endif